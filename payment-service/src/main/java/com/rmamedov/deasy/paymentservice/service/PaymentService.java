package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.paymentservice.client.OrderClient;
import com.rmamedov.deasy.paymentservice.converter.PaymentToPayResponseConverter;
import com.rmamedov.deasy.paymentservice.exception.AccountNotFoundException;
import com.rmamedov.deasy.paymentservice.exception.PaymentFailException;
import com.rmamedov.deasy.paymentservice.model.controller.PayRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PayResponse;
import com.rmamedov.deasy.paymentservice.model.repository.Account;
import com.rmamedov.deasy.paymentservice.model.repository.Payment;
import com.rmamedov.deasy.paymentservice.repository.AccountRepository;
import com.rmamedov.deasy.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderClient orderClient;

    private final PaymentRepository paymentRepository;

    private final AccountRepository accountRepository;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final PaymentToPayResponseConverter responseConverter;

    @Transactional
    public Mono<PayResponse> pay(final PayRequest request) {
        return orderClient.getById(request.getOrderId())
                .log()
                .flatMap(orderDto ->
                        Mono.zip(
                                accountRepository.findByBankAccountNumber(request.getSenderBankAccountNumber()),
                                accountRepository.findByBankAccountNumber(request.getReceiverBankAccountNumber())
                        )
                                .flatMap(zip -> {
                                    final Account senderAccount = zip.getT1();
                                    final Account receiverAccount = zip.getT2();
                                    senderAccount.setBalance(senderAccount.getBalance().subtract(orderDto.getTotalPrice()));
                                    receiverAccount.setBalance(receiverAccount.getBalance().add(orderDto.getTotalPrice()));
                                    return paymentRepository.save(buildPayment(orderDto, senderAccount, receiverAccount))
                                            .doOnNext(saved -> applicationKafkaSender.send(orderDto));
                                })
                                .switchIfEmpty(Mono.error(new AccountNotFoundException("One or both accounts that participates in payment not found.")))
                )
                .map(responseConverter::convert)
                .switchIfEmpty(Mono.error(new PaymentFailException("Payment has failed. Order id: '" + request.getOrderId() + "'")));
    }

    private Payment buildPayment(final OrderDto orderDto,
                                 final Account senderAccount,
                                 final Account receiverAccount) {

        return Payment.builder()
                .orderId(orderDto.getId())
                .orderSum(orderDto.getTotalPrice())
                .senderBankAccountNum(senderAccount.getBankAccountNumber())
                .receiverBankAccountNum(receiverAccount.getBankAccountNumber())
                .build();
    }

}
