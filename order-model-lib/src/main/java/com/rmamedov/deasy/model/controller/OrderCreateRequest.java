package com.rmamedov.deasy.model.controller;

import com.rmamedov.deasy.model.kafka.OrderPosition;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class OrderCreateRequest {

    @NotBlank
    private String description;

    @NotBlank
    private String consumerAddress;

    @NotBlank
    private String restaurantAddress;

    @NotEmpty
    private Set<OrderPosition> orderPositions;

}
