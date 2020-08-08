package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverter;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProcessOrderServiceImpl implements ProcessOrderService {

    private final ApplicationKafkaSender<OrderMessage> applicationKafkaSender;

    private final OrderService orderService;

    private final OrderToOrderMessageConverter orderToOrderMessageConverter;

    public ProcessOrderServiceImpl(@Qualifier("readyToCookSender") ApplicationKafkaSender<OrderMessage> applicationKafkaSender,
                                   OrderService orderService,
                                   OrderToOrderMessageConverter orderToOrderMessageConverter) {

        this.applicationKafkaSender = applicationKafkaSender;
        this.orderService = orderService;
        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
    }

    @Override
    @Transactional
    public Mono<Order> updateAndSend(final Order order) {
        return Mono.just(order)
                .flatMap(payedOrder -> orderService.findById(payedOrder.getId())
                        .doOnNext(savedOrder -> {
                            savedOrder.setTransactionId(payedOrder.getTransactionId());
                            savedOrder.setPaymentStatus(payedOrder.getPaymentStatus());
                        })
                        .flatMap(orderService::save)
                        .doOnSuccess(r -> {
                            final OrderMessage orderMessage = orderToOrderMessageConverter.convert(payedOrder);
                            applicationKafkaSender.send(orderMessage, orderMessage.getId());
                        })
                );
    }

}
