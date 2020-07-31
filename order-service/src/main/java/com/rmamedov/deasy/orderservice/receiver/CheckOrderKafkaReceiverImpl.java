package com.rmamedov.deasy.orderservice.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.orderservice.config.mongo.MongoConfigurationProperties;
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

import static java.time.Duration.ofMillis;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckOrderKafkaReceiverImpl implements CheckOrderKafkaReceiver {

    private ApplicationKafkaReceiver<OrderMessage> kafkaReceiver;

    private final CheckOrderService checkOrderService;

    private final OrderMessageToOrderConverter orderMessageToOrderConverter;

    private final MongoConfigurationProperties mongoProperties;

    private final KafkaReceiverProperties receiverProperties;

    private final List<TopicProperties> topicConfigurationList;

    @PostConstruct
    public void init() {
        final List<String> topicNames = topicConfigurationList.stream()
                .map(TopicProperties::getName)
                .filter(name -> name.startsWith("checked"))
                .collect(Collectors.toList());
        kafkaReceiver = new ApplicationKafkaReceiver<>(receiverProperties, topicNames);
    }

    @Override
    public Flux<OrderCheckInfo> listenCheckedOrders() {
        return kafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final var order = orderMessageToOrderConverter.convert(receiverRecord.value());
                    return checkOrderService.updateOrderAfterEtlCheck(order)
                            .doOnSuccess(checkInfo -> receiverRecord.receiverOffset().acknowledge())
                            .doOnError(ex -> log.warn("Concurrent access to DB with retry: {}", ex.getMessage()))
                            .retryBackoff(
                                    mongoProperties.getNumRetries(),
                                    ofMillis(mongoProperties.getFirstBackoff()),
                                    ofMillis(mongoProperties.getMaxBackoff()),
                                    Schedulers.elastic()
                            );
                });
    }

}
