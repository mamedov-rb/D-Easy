package com.rmamedov.deasy.orderservice.model.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {
        "orderId"
})
public class OrderCreateResponse {

    private String orderId;

}
