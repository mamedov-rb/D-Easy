package com.rmamedov.deasy.model.repository;

import com.rmamedov.deasy.model.kafka.Address;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderPosition;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private String description;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private Address consumerAddress;

    private Address restorauntAddress;

    private Set<OrderPosition> orderPositions;

    private Set<CheckStatus> checkStatuses = new HashSet<>();

    private Map<String, String> checkDetails = new HashMap<>();

    private LocalDateTime created = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updated;

}
