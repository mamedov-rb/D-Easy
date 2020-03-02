package com.rmamedov.deasy.orderservice.repository.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "orders")
public class Order {

    private String id = UUID.randomUUID().toString();

    private String name;

    private String description;

    @CreatedDate // TODO: not working.
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

}
