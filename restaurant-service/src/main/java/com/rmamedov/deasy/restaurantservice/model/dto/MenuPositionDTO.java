package com.rmamedov.deasy.restaurantservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class MenuPositionDTO {

    private String id;

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
