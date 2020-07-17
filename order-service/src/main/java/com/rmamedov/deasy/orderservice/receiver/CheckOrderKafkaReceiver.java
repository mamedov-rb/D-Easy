package com.rmamedov.deasy.orderservice.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.orderservice.converter.OrderMessageToOrderConverter;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.service.CheckOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckOrderKafkaReceiver {

    private ApplicationKafkaReceiver<OrderMessage> kafkaReceiver;

    private final CheckOrderService checkOrderService;

    private final OrderMessageToOrderConverter orderMessageToOrderConverter;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final List<TopicConfigurationProperties> topicConfigurationList;

    @PostConstruct
    public void init() {
        final List<String> topicNames = topicConfigurationList.stream()
                .map(TopicConfigurationProperties::getName)
                .filter(name -> !name.contains("new"))
                .collect(Collectors.toList());
        kafkaReceiver = new ApplicationKafkaReceiver<>(receiverProperties, topicNames);
    }

    public Flux<OrderCheckInfo> listenCheckedOrders() {
        return kafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final var order = orderMessageToOrderConverter.convert(receiverRecord.value());
                    return checkOrderService.updateOrderAfterEtlCheck(order)
                            .doOnSuccess(checkInfo -> receiverRecord.receiverOffset().acknowledge())
                            .doOnError(ex -> log.error("Exception has occurred: ", ex));
                })
                .subscribeOn(Schedulers.single());
    }

}