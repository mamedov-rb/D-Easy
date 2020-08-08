package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.application.model.exceptions.OrderNotFoundException;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Mono<Order> save(final Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Order> findByCriteria(final String id,
                                      final String checkStatus,
                                      final String paymentStatus) {

        return orderRepository.findByIdAndCheckStatusesInAndPaymentStatus(id, Set.of(checkStatus), paymentStatus)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(
                        String.format("Order with: id '%s', checkStatus '%s', paymentStatus '%s' - Not Found", id, checkStatus, paymentStatus)
                )));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Order> findById(final String id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(String.format("Order with id: '%s' - Not Found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

}
