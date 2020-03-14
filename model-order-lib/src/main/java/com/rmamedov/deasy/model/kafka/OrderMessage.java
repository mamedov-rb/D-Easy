package com.rmamedov.deasy.model.kafka;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderMessage {

    private String id;

    private String name;

    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;

}
