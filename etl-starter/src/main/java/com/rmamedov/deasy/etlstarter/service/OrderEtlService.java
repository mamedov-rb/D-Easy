package com.rmamedov.deasy.etlstarter.service;

import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface OrderEtlService {

    Mono<OrderMessage> check(Mono<OrderMessage> orderMessage);

}
