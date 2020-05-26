package com.rmamedov.deasy.model.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {

    private final String timestamp;

    private final String message;

}

