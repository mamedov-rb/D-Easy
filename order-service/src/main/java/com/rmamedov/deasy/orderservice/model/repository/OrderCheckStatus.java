package com.rmamedov.deasy.orderservice.model.repository;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "order_check_details")
public class OrderCheckStatus {

    @Id
    private String id = UUID.randomUUID().toString();

    private Set<CheckStatus> checkStatuses;

    private Map<String, String> checkDetails;

    private LocalDateTime created = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updated;

}
