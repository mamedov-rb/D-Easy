package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.converter.OrderToOrderCheckStatusConverter;
import com.rmamedov.deasy.model.repository.Order;
import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatus;
import com.rmamedov.deasy.orderservice.repository.OrderCheckStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCheckStatusService {

    private final OrderCheckStatusRepository orderCheckStatusRepository;

    private final OrderToOrderCheckStatusConverter orderToOrderCheckStatusConverter;

    public Mono<OrderCheckStatus> save(final Order order) {
        return Mono.just(order)
                .map(orderToOrderCheckStatusConverter::convert)
                .flatMap(orderCheckStatusRepository::save);
    }

    public Flux<OrderCheckStatus> findAll() {
        return orderCheckStatusRepository.findAll();
    }

}
