package com.rmamedov.deasy.model.controller;

import com.rmamedov.deasy.model.kafka.Address;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderPosition;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class OrderInfoResponse {

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

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

}
