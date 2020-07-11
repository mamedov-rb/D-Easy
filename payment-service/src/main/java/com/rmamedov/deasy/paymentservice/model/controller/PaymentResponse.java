package com.rmamedov.deasy.paymentservice.model.controller;

import com.rmamedov.deasy.model.kafka.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private String orderId;

    private String transactionId;

    private PaymentStatus status;

    private BigDecimal sum;

    private String senderBankAccountNum;

    private String receiverBankAccountNum;

    private LocalDateTime transactionTimestamp;

}
