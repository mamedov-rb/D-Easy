package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.model.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import com.rmamedov.deasy.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Mono<Order> save(final Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Mono<Order> findByIdAndCheckStatus(final String id,
                                              final String checkStatus,
                                              final String payStatus) {

        return orderRepository.findByIdAndCheckStatusesInAndPaymentStatus(id, Set.of(checkStatus), payStatus)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(
                        String.format("Order with: id '%s', checkStatus '%s', payStatus '%s' - Not Found", id, checkStatus, payStatus)
                )));
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
