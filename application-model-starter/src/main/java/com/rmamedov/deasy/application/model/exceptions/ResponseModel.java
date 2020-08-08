package com.rmamedov.deasy.application.model.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseModel {

    private final LocalDateTime timestamp;

    private final String message;

}

