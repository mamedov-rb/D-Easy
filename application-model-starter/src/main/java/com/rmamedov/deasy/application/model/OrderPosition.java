package com.rmamedov.deasy.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {
        "name", "price", "quantity", "discount"
})
public class OrderPosition {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal discount;

    @NotNull
    @Positive
    private Double weight;

    @NotNull
    @Positive
    private Double width;

    @NotNull
    @Positive
    private Double length;

    @NotNull
    @Positive
    private Double height;

    private String additionalInfo;

}
