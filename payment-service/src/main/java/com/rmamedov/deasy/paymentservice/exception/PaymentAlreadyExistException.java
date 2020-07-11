package com.rmamedov.deasy.paymentservice.exception;

public class PaymentAlreadyExistException extends RuntimeException {

    public PaymentAlreadyExistException(String message) {
        super(message);
    }

}
