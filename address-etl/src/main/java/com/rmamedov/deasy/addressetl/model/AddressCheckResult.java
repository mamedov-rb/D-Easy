package com.rmamedov.deasy.addressetl.model;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"orderId"})
public class AddressCheckResult {

    private String id = UUID.randomUUID().toString();

    private String orderId;

    private String transactionId;

    private String orderName;

    private String consumerAddress;

    private String restaurantAddress;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private LocalDateTime originCreated;

    private LocalDateTime created = LocalDateTime.now();

}
