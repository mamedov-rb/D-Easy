package com.rmamedov.deasy.application.model.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String OrderMessage) {
        super(OrderMessage);
    }

}
