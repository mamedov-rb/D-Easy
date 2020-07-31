package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.orderservice.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.orderservice.model.controller.OrderInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderController {

    ResponseEntity<Mono<OrderCreateResponse>> create(Mono<OrderCreateRequest> createRequest);

    ResponseEntity<Flux<OrderCheckInfo>> statuses();

    ResponseEntity<Mono<OrderInfo>> findByIdAndCheckStatus(String id, String checkStatus, String paymentStatus);

    ResponseEntity<Flux<OrderInfo>> findAll();

}
