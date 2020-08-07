package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
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

@Slf4j
@Service
public class CheckOrderServiceImpl implements CheckOrderService {

    public static final Set<CheckStatus> FULLY_CHECKED_SET = Set.of(
            CheckStatus.ADDRESSES_CHECKED,
            CheckStatus.ORDER_MENU_CHECKED,
            CheckStatus.COURIER_CHECKED
    );

    private final ApplicationKafkaSender<OrderMessage> applicationKafkaSender;

    private final OrderService orderService;

    private final OrderToOrderMessageConverter orderToOrderMessageConverter;

    private final OrderToOrderStatusInfoConverter orderToOrderStatusInfoConverter;

    public CheckOrderServiceImpl(@Qualifier("newOrdersSender") ApplicationKafkaSender<OrderMessage> applicationKafkaSender,
                                 OrderService orderService,
                                 OrderToOrderMessageConverter orderToOrderMessageConverter,
                                 OrderToOrderStatusInfoConverter orderToOrderStatusInfoConverter) {

        this.applicationKafkaSender = applicationKafkaSender;
        this.orderService = orderService;
        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
        this.orderToOrderStatusInfoConverter = orderToOrderStatusInfoConverter;
    }

    @Override
    @Transactional
    public Mono<OrderCreateResponse> createAndSend(final Order order) {
        return orderService.save(order.setAsNew())
                .map(orderToOrderMessageConverter::convert)
                .doOnNext(orderMeassage -> applicationKafkaSender.send(orderMeassage, orderMeassage.getId()))
                .map(orderMessage -> new OrderCreateResponse(orderMessage.getId()));
    }

    @Override
    @Transactional
    public Mono<OrderCheckInfo> updateOrderAfterEtlCheck(final Order order) {
        return Mono.just(order)
                .flatMap(checkedOrder -> orderService.findById(checkedOrder.getId())
                        .doOnSuccess(foundOrder -> {
                            foundOrder.getCheckStatuses().addAll(checkedOrder.getCheckStatuses());
                            foundOrder.getCheckDetails().putAll(checkedOrder.getCheckDetails());
                            if (foundOrder.getCheckStatuses().containsAll(FULLY_CHECKED_SET)) {
                                foundOrder.setCheckStatuses(Set.of(CheckStatus.FULLY_CHECKED));
                                log.info(String.format("Order with id: '%s' is FULLY_CHECKED", foundOrder.getId()));
                            }
                        })
                        .flatMap(orderService::save)
                        .map(orderToOrderStatusInfoConverter::convert)
                );
    }

}
