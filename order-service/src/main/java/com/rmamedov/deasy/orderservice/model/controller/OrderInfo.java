package com.rmamedov.deasy.orderservice.model.controller;

import com.rmamedov.deasy.application.model.CheckStatus;
import com.rmamedov.deasy.application.model.OrderPosition;
import com.rmamedov.deasy.application.model.PaymentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {
        "id", "name", "description"
})
public class OrderInfo {

    private String id;

    private String name;

    private String description;

    private BigDecimal totalPrice;

    private BigDecimal totalPriceAfterDiscount;

    private Double totalWeight;

    private Double totalVolume;

    private String consumerAddress;

    private String restaurantAddress;

    private Set<OrderPosition> orderPositions;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime created;

    private LocalDateTime updated;

}
