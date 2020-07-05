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

import java.math.BigDecimal;

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
                .flatMap(orderDto -> findBothAccounts(request) //TODO 2020-07-05 rustammamedov: update accounts after payment.
                        .flatMap(zip -> paymentRepository.save(withdrawAndCreatePayment(orderDto, zip))
                                .doOnNext(payment -> orderDto.setTransactionId("TX_" + payment.getTransactionId()))
                                .doOnSuccess(payment -> orderDto.setPaymentStatus(PaymentStatus.SUCCESS))
                                .doOnError(throwable -> orderDto.setPaymentStatus(PaymentStatus.FAILED))
                                .doFinally(payment -> applicationKafkaSender.send(orderDto)) //TODO 2020-07-05 rustammamedov: What if message not delivered?
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
        final BigDecimal totalPriceAfterDiscount = orderDto.getTotalPriceAfterDiscount();
        senderAccount.setBalance(senderAccount.getBalance().subtract(totalPriceAfterDiscount)); //TODO 2020-07-05 rustammamedov: check balance before withdraw.
        receiverAccount.setBalance(receiverAccount.getBalance().add(totalPriceAfterDiscount));
        return Payment.builder()
                .orderId(orderDto.getId())
                .status(PaymentStatus.SUCCESS) //TODO 2020-07-05 rustammamedov: set status with depends of balance.
                .orderSum(totalPriceAfterDiscount)
                .senderBankAccountNum(senderAccount.getBankAccountNumber())
                .receiverBankAccountNum(receiverAccount.getBankAccountNumber())
                .build();
    }

}
