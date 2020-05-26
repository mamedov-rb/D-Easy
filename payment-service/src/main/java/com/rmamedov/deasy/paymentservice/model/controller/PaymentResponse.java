package com.rmamedov.deasy.paymentservice.model.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private String orderId;

    private String transactionId;

    private BigDecimal orderSum;

    private String senderBankAccountNum;

    private String receiverBankAccountNum;

    private LocalDateTime transactionTimestamp;

}
