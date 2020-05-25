package com.rmamedov.deasy.paymentservice.model.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private String orderId;

    private String transactionId;

    private BigDecimal orderSum;

    private String senderBankAccount;

    private String receiverBankAccount;

    private LocalDateTime transactionTimestamp;

}
