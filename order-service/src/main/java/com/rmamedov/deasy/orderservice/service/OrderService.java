package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.converter.OrderToOrderMessageConverter;
import com.rmamedov.deasy.model.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.model.repository.Order;
import com.rmamedov.deasy.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final OrderToOrderMessageConverter orderToOrderMessageConverter;

    public OrderService(OrderRepository orderRepository,
                        @Qualifier("inProgressOrdersSender") ApplicationKafkaSender applicationKafkaSender,
                        OrderToOrderMessageConverter orderToOrderMessageConverter) {

        this.orderRepository = orderRepository;
        this.applicationKafkaSender = applicationKafkaSender;
        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
    }

    public Mono<Order> create(final Order order) {
        return orderRepository.save(order)
                .flatMap(saved -> {
                    applicationKafkaSender.send(orderToOrderMessageConverter.convert(saved));
                    return Mono.just(saved);
                });
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
