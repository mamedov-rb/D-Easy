package com.rmamedov.deasy.model.kafka;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"name", "price", "quantity"})
public class OrderPosition {

    private String name;

    private Integer quantity;

    private BigDecimal price;

    private Double weight;

    private Double width;

    private Double height;

    private String additionalInfo;

}
