package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import reactor.core.publisher.Mono;

public interface CheckOrderService {

    Mono<OrderCreateResponse> createAndSend(Order order);

    Mono<OrderCheckInfo> updateOrderAfterEtlCheck(Order order);

}
