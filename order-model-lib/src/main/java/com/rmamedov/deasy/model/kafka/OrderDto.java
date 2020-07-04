package com.rmamedov.deasy.model.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class OrderDto {

    private String id;

    private String name;

    private String description;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private Address consumerAddress;

    private Address restaurantAddress;

    private Set<OrderPosition> orderPositions;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime created;

    private LocalDateTime updated;

}
