package com.rmamedov.deasy.orderservice.model.controller;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class OrderStatusInfo {

    private String orderId;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private LocalDateTime created;

    private LocalDateTime updated;

}
