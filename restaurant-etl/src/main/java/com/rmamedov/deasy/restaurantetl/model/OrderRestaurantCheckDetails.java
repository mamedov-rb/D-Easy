package com.rmamedov.deasy.restaurantetl.model;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderPosition;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "order_restaurant_check_details")
public class OrderRestaurantCheckDetails {

    @Id
    private String id = UUID.randomUUID().toString();

    private String orderId;

    private String transactionId;

    private String name;

    private String description;

    private Set<OrderPosition> orderPositions;

    private Set<CheckStatus> checkStatuses = new HashSet<>();

    private Map<String, String> checkDetails = new HashMap<>();

    private LocalDateTime originCreated;

    private LocalDateTime created = LocalDateTime.now();

}
