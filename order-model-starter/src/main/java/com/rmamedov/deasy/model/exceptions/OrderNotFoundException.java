package com.rmamedov.deasy.model.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String OrderMessage) {
        super(OrderMessage);
    }

}
