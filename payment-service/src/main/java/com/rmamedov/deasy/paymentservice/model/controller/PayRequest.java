package com.rmamedov.deasy.paymentservice.model.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class PayRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String senderBankAccountNumber;

    @NotBlank
    private String receiverBankAccountNumber;

}
