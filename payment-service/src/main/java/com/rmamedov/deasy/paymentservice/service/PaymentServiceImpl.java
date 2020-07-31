package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.model.PaymentStatus;
import com.rmamedov.deasy.paymentservice.client.OrderClient;
import com.rmamedov.deasy.paymentservice.converter.PaymentToPayResponseConverter;
import com.rmamedov.deasy.paymentservice.exception.AccountNotFoundException;
import com.rmamedov.deasy.paymentservice.exception.NotEnoughMoneyException;
import com.rmamedov.deasy.paymentservice.exception.PaymentAlreadyExistException;
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
public class PaymentServiceImpl implements PaymentService {

    private final OrderClient orderClient;

    private final PaymentRepository paymentRepository;

    private final AccountService accountService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final PaymentToPayResponseConverter responseConverter;

    @Override
    @Transactional
    public Mono<PaymentResponse> payForNewFullyCheckedOrder(final PaymentRequest request) {
        final var orderId = request.getOrderId();
        final var checkStatus = CheckStatus.FULLY_CHECKED.name();
        final var paymentStatus = PaymentStatus.NEW.name();
        return orderClient.findByCriteria(orderId, checkStatus, paymentStatus)
                .flatMap(orderMessage -> paymentRepository.existsByOrderId(orderId)
                        .filter(paymentExists -> !paymentExists)
                        .flatMap(paymentExists -> findBothAccounts(request)
                                .flatMap(zippedAccounts -> processPayment(orderMessage, zippedAccounts)
                                        .doOnNext(payment -> accountService.saveAll(zippedAccounts.getT1(), zippedAccounts.getT2()).subscribe())
                                )
                                .flatMap(payment -> paymentRepository.save(payment)
                                        .doOnNext(onNext -> orderMessage.setTransactionId(payment.getTransactionId()))
                                        .doOnSuccess(onSuccess -> orderMessage.setPaymentStatus(PaymentStatus.SUCCESS))
                                        .doOnError(throwable -> orderMessage.setPaymentStatus(PaymentStatus.FAILED))
                                        .doFinally(finalSignal -> applicationKafkaSender.send(orderMessage))
                                )
                        )
                        .switchIfEmpty(Mono.error(new PaymentAlreadyExistException(String.format("Payment with orderId '%s' - Already exists.", orderId))))
                )
                .map(payment -> {
                    log.info("Order successfully payed: {}", payment);
                    return responseConverter.convert(payment);
                });
    }

    private Mono<Tuple2<Account, Account>> findBothAccounts(PaymentRequest request) {
        return Mono.zip(
                accountService.findByBankAccountNumber(request.getSenderBankAccountNumber()),
                accountService.findByBankAccountNumber(request.getReceiverBankAccountNumber())
        ).switchIfEmpty(Mono.error(new AccountNotFoundException("One or both accounts that participates in payment - Not found.")));
    }

    private Mono<Payment> processPayment(final OrderMessage orderMessage, final Tuple2<Account, Account> zip) {
        final Account senderAccount = zip.getT1();
        final Account receiverAccount = zip.getT2();
        final BigDecimal sumForWithdraw = orderMessage.getTotalPriceAfterDiscount();
        final var senderBalance = senderAccount.getBalance();
        final var receiverBalance = receiverAccount.getBalance();

        ensureBalance(senderBalance, sumForWithdraw);
        withdraw(senderAccount, receiverAccount, sumForWithdraw, senderBalance, receiverBalance);
        return Mono.just(
                Payment.builder()
                        .orderId(orderMessage.getId())
                        .status(PaymentStatus.SUCCESS)
                        .sum(sumForWithdraw)
                        .senderBankAccountNum(senderAccount.getBankAccountNumber())
                        .receiverBankAccountNum(receiverAccount.getBankAccountNumber())
                        .build()
        );
    }

    private void ensureBalance(final BigDecimal senderBalance, final BigDecimal sumForWithdraw) {
        if (senderBalance.compareTo(sumForWithdraw) < 0) {
            throw new NotEnoughMoneyException("Customer has't enough money for pay.");
        }
    }

    private void withdraw(final Account senderAccount,
                          final Account receiverAccount,
                          final BigDecimal sumForWithdraw,
                          final BigDecimal senderBalance,
                          final BigDecimal receiverBalance) {

        final var subtract = senderBalance.subtract(sumForWithdraw);
        final var add = receiverBalance.add(sumForWithdraw);
        senderAccount.setBalance(subtract);
        receiverAccount.setBalance(add);
    }

}
