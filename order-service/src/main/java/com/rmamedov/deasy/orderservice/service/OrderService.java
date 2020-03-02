package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.orderservice.repository.OrderRepository;
import com.rmamedov.deasy.orderservice.repository.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Mono<Order> create(final Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> findById(final String id) {
        return orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException("Order with id: '" + id + "' - Not Found")));
    }

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

}
