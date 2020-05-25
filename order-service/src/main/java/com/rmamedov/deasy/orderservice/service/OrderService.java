package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.model.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import com.rmamedov.deasy.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Mono<Order> save(final Order order) {
        return orderRepository.save(order);
//                .doOnNext(saved -> Assert.isTrue(saved.getId() != null, "Id is null"));
    }

    @Transactional(readOnly = true)
    public Mono<Order> findById(final String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(String.format("Order with id: '%s' - Not Found", id))));
    }

    @Transactional(readOnly = true)
    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

}
