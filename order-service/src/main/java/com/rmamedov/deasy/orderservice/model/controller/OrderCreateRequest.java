package com.rmamedov.deasy.orderservice.model.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class OrderCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

}
