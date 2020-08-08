package com.rmamedov.deasy.paymentservice.client;

import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface OrderClient {

    Mono<OrderMessage> findByCriteria(String id, String checkStatus, String payStatus);

}
