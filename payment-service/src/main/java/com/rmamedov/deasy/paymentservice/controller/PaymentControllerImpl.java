package com.rmamedov.deasy.paymentservice.controller;

import com.rmamedov.deasy.paymentservice.model.controller.PaymentRequest;
import com.rmamedov.deasy.paymentservice.model.controller.PaymentResponse;
import com.rmamedov.deasy.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    @PostMapping(
            path = "/pay",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Mono<PaymentResponse>> pay(@RequestBody @Validated final Mono<PaymentRequest> request) {
        final var responseMono = request.flatMap(paymentService::payForNewFullyCheckedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMono);
    }

}
