package com.rmamedov.deasy.paymentservice.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Document(value = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String orderId;

    @NotBlank
    private String transactionId = UUID.randomUUID().toString();

    @NotNull
    @Positive
    private BigDecimal orderSum;

    @NotBlank
    private String senderBankAccountNum;

    @NotBlank
    private String receiverBankAccountNum;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

}
