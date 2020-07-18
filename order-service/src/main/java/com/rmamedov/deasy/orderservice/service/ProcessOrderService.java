package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.model.repository.Order;
import reactor.core.publisher.Mono;

public interface ProcessOrderService {

    Mono<Order> updateAndSend(Order order);

}
