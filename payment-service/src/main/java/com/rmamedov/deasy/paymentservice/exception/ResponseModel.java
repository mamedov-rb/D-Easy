package com.rmamedov.deasy.paymentservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {

    private final String timestamp;

    private final String message;

}

