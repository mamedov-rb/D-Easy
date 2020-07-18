package com.rmamedov.deasy.restaurantetl.service;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface RestaurantEtlService {

    Mono<OrderMessage> check(Mono<OrderMessage> OrderMessage);

}
