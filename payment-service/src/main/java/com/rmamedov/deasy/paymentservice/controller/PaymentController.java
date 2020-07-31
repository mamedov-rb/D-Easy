package com.rmamedov.deasy.paymentservice.controller;

import com.rmamedov.deasy.paymentservice.model.controller.PaymentRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface PaymentController {

    ResponseEntity<Mono<PaymentResponse>> pay(Mono<PaymentRequest> request);

}
