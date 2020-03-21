package com.rmamedov.deasy.model.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderMessage {

    private String id;

    private String name;

    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private Address consumerAddress;

    private Address restorauntAddress;

    private Set<OrderPosition> orderPositions;

    private DeliveryStatus deliveryStatus = DeliveryStatus.NEW;

}
