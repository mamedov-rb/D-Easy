package com.rmamedov.deasy.courieretl.service;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface CourierEtlService {

    Mono<OrderMessage> check(Mono<OrderMessage> OrderMessage);

}
