package com.rmamedov.deasy.paymentservice.controller;

import com.rmamedov.deasy.paymentservice.model.controller.PaymentRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentResponse;
import com.rmamedov.deasy.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(
            path = "/pay",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<PaymentResponse> pay(@RequestBody @Validated final Mono<PaymentRequest> request) {
        return request.flatMap(paymentService::payForNewFullyCheckedOrder);
    }

}
