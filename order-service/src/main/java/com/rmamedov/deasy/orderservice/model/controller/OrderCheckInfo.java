package com.rmamedov.deasy.orderservice.model.controller;

import com.rmamedov.deasy.application.model.CheckStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {
        "created", "updated"
})
public class OrderCheckInfo {

    private String orderId;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private LocalDateTime created;

    private LocalDateTime updated;

}
