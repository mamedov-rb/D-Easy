package com.rmamedov.deasy.goodsservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class GoodDTO {

    private String id;

    private String organisationAddress;

    private String name;

    private BigDecimal price;

    private BigDecimal discount;

    private Double weight;

    private Double width;

    private Double length;

    private Double height;

    private String additionalInfo;

    private Set<String> images;

}
