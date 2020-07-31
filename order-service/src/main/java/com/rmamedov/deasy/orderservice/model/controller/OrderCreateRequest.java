package com.rmamedov.deasy.orderservice.model.controller;

import com.rmamedov.deasy.model.OrderPosition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {
        "description", "consumerAddress", "restaurantAddress"
})
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
