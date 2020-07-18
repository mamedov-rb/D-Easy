package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.controller.OrderInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderController {

    Mono<OrderCreateResponse> create(Mono<OrderCreateRequest> createRequest);

    Flux<OrderCheckInfo> statuses();

    Mono<OrderInfo> findByIdAndCheckStatus(String id, String checkStatus, String paymentStatus);

    Flux<OrderInfo> findAll();

}
