package com.rmamedov.deasy.addressetl.service;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface AddressEtlService {

    Mono<OrderMessage> check(Mono<OrderMessage> OrderMessage);

}
