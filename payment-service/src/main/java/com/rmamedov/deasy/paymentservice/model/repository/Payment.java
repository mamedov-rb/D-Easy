package com.rmamedov.deasy.paymentservice.model.repository;

import com.rmamedov.deasy.application.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Document(value = "payments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {
        "id", "orderId", "status", "sum"
})
public class Payment {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String transactionId = "TX_" + UUID.randomUUID().toString();

    @NotBlank
    private String orderId;

    @NotNull
    private PaymentStatus status;

    @NotNull
    @Positive
    private BigDecimal sum;

    @NotBlank
    private String senderBankAccountNum;

    @NotBlank
    private String receiverBankAccountNum;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    @Builder
    public Payment(String orderId,
                   PaymentStatus status,
                   BigDecimal sum,
                   String senderBankAccountNum,
                   String receiverBankAccountNum) {

        this.orderId = orderId;
        this.status = status;
        this.sum = sum;
        this.senderBankAccountNum = senderBankAccountNum;
        this.receiverBankAccountNum = receiverBankAccountNum;
    }

}
