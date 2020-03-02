package com.rmamedov.deasy.orderservice.controller.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderInfoResponse {

    private String id;

    private String name;

    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;

}
