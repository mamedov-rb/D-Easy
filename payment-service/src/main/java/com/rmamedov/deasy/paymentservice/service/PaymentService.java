package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.paymentservice.model.controller.PaymentRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentResponse;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Mono<PaymentResponse> payForNewFullyCheckedOrder(PaymentRequest request);

}
