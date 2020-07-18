package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.model.repository.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Order> save(Order order);

    Mono<Order> findByCriteria(String id, String checkStatus, String paymentStatus);

    Mono<Order> findById(String id);

    Flux<Order> findAll();

}
