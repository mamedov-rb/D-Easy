package com.rmamedov.addressservice.service;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    public Mono<OrderMessage> check(final OrderMessage orderMessage) {
        log.info("Address was checked with result: SUCCESS");
        return Mono.just(orderMessage); // TODO 2020-03-19 rustammamedov: Do real check;
    }

}
