package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.orderservice.config.properties.MongoConfigurationProperties;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverter;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderStatusInfoConverter;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Set;

import static java.time.Duration.ofMillis;

@Slf4j
@Service
public class CheckOrderService {

    public static final Set<CheckStatus> FULLY_CHECKED_SET = Set.of(
            CheckStatus.ADDRESSES_CHECKED,
            CheckStatus.ORDER_MENU_CHECKED,
            CheckStatus.COURIER_CHECKED
    );

    private final ApplicationKafkaSender applicationKafkaSender;

    private final OrderService orderService;

    private final MongoConfigurationProperties mongoProperties;

    private final OrderToOrderMessageConverter orderToOrderMessageConverter;

    private final OrderToOrderStatusInfoConverter orderToOrderStatusInfoConverter;

    public CheckOrderService(@Qualifier("newOrdersSender") ApplicationKafkaSender applicationKafkaSender,
                             OrderService orderService,
                             MongoConfigurationProperties mongoProperties,
                             OrderToOrderMessageConverter orderToOrderMessageConverter,
                             OrderToOrderStatusInfoConverter orderToOrderStatusInfoConverter) {

        this.applicationKafkaSender = applicationKafkaSender;
        this.orderService = orderService;
        this.mongoProperties = mongoProperties;
        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
        this.orderToOrderStatusInfoConverter = orderToOrderStatusInfoConverter;
    }

    @Transactional
    public Mono<OrderCreateResponse> createAndSend(final Order order) {
        return orderService.save(order)
                .map(orderToOrderMessageConverter::convert)
                .flatMap(OrderMessage -> {
                    applicationKafkaSender.send(OrderMessage);
                    return Mono.just(new OrderCreateResponse(order.getId()));
                });
    }

    @Transactional
    public Mono<OrderCheckInfo> updateOrderAfterEtlCheck(final Order order) {
        return Mono.just(order)
                .flatMap(incomingOrder -> orderService.findById(incomingOrder.getId())
                        .doOnNext(savedOrder -> {
                            savedOrder.getCheckStatuses().addAll(incomingOrder.getCheckStatuses());
                            savedOrder.getCheckDetails().putAll(incomingOrder.getCheckDetails());
                            if (savedOrder.getCheckStatuses().containsAll(FULLY_CHECKED_SET)) {
                                savedOrder.setCheckStatuses(Set.of(CheckStatus.FULLY_CHECKED));
                            }
                        })
                        .flatMap(orderService::save)
                        .map(orderToOrderStatusInfoConverter::convert)
                )
                .retryBackoff(
                        mongoProperties.getNumRetries(),
                        ofMillis(mongoProperties.getFirstBackoff()),
                        ofMillis(mongoProperties.getMaxBackoff())
                );
    }

}
