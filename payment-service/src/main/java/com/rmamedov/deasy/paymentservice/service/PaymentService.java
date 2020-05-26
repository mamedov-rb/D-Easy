package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.model.kafka.PaymentStatus;
import com.rmamedov.deasy.paymentservice.client.OrderClient;
import com.rmamedov.deasy.paymentservice.converter.PaymentToPayResponseConverter;
import com.rmamedov.deasy.paymentservice.exception.AccountNotFoundException;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentResponse;
import com.rmamedov.deasy.paymentservice.model.repository.Account;
import com.rmamedov.deasy.paymentservice.model.repository.Payment;
import com.rmamedov.deasy.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderClient orderClient;

    private final PaymentRepository paymentRepository;

    private final AccountService accountService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final PaymentToPayResponseConverter responseConverter;

    @Transactional
    public Mono<PaymentResponse> payForNewFullyCheckedOrder(final PaymentRequest request) {
        return orderClient.findByIdAndCheckStatus(
                request.getOrderId(),
                CheckStatus.FULLY_CHECKED.name(),
                PaymentStatus.NEW.name()
        )
                .flatMap(order -> findBothAccounts(request)
                        .flatMap(zip -> paymentRepository.save(withdrawAndCreatePayment(order, zip))
                                .doOnSuccess(saved -> {
                                    order.setPaymentStatus(PaymentStatus.SUCCESS);
                                    order.setTransactionId(saved.getTransactionId());
                                })
                                .doOnError(saved -> order.setPaymentStatus(PaymentStatus.FAILED))
                                .doFinally(saved -> applicationKafkaSender.send(order))
                        )
                )
                .map(responseConverter::convert);
    }

    private Mono<Tuple2<Account, Account>> findBothAccounts(PaymentRequest request) {
        return Mono.zip(
                accountService.findByBankAccountNumber(request.getSenderBankAccountNumber()),
                accountService.findByBankAccountNumber(request.getReceiverBankAccountNumber())
        )
                .switchIfEmpty(Mono.error(new AccountNotFoundException("One or both accounts that participates in payment - Not found.")));
    }

    private Payment withdrawAndCreatePayment(final OrderDto orderDto, final Tuple2<Account, Account> zip) {
        final Account senderAccount = zip.getT1();
        final Account receiverAccount = zip.getT2();
        senderAccount.setBalance(senderAccount.getBalance().subtract(orderDto.getTotalPrice()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(orderDto.getTotalPrice()));
        return Payment.builder()
                .orderId(orderDto.getId())
                .orderSum(orderDto.getTotalPrice())
                .senderBankAccountNum(senderAccount.getBankAccountNumber())
                .receiverBankAccountNum(receiverAccount.getBankAccountNumber())
                .build();
    }

}
