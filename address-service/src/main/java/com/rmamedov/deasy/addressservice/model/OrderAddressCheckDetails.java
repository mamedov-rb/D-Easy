package com.rmamedov.deasy.addressservice.model;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "order_address_check_details")
public class OrderAddressCheckDetails {

    @Id
    private String id = UUID.randomUUID().toString();

    private String orderId;

    private String transactionId;

    private String consumerAddress;

    private String restaurantAddress;

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private LocalDateTime originCreated;

    private LocalDateTime created = LocalDateTime.now();

}
