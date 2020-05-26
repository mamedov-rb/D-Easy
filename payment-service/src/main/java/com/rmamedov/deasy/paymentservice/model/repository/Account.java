package com.rmamedov.deasy.paymentservice.model.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document(value = "accounts")
@Validated
public class Account {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Indexed(unique = true)
    private String bankAccountNumber;

    @NotNull
    private Holder holder;

    @NotNull
    private BigDecimal balance;

}
