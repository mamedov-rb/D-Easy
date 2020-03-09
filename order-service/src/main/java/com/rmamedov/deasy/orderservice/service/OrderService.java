package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.config.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverter;
import com.rmamedov.deasy.orderservice.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.orderservice.kafka.producer.InProgressOrdersKafkaProducer;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import com.rmamedov.deasy.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final TopicConfigurationProperties topicProperties;

    private final OrderRepository orderRepository;

    private final InProgressOrdersKafkaProducer inProgressOrdersKafkaProducer;

    private final OrderToOrderMessageConverter orderToOrderMessageConverter;

    public OrderService(@Qualifier("inProgressOrdersTopicProp") TopicConfigurationProperties topicProperties,
                        OrderRepository orderRepository,
                        InProgressOrdersKafkaProducer inProgressOrdersKafkaProducer,
                        OrderToOrderMessageConverter orderToOrderMessageConverter) {

        this.topicProperties = topicProperties;
        this.orderRepository = orderRepository;
        this.inProgressOrdersKafkaProducer = inProgressOrdersKafkaProducer;
        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
    }

    @Transactional
    public Mono<Order> create(final Order order) {
        return orderRepository.save(order)
                .map(savedOrder -> {
                    inProgressOrdersKafkaProducer.send(
                            topicProperties.getName(),
                            order.getId(),
                            orderToOrderMessageConverter.convert(order)
                    );
                    return savedOrder;
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
