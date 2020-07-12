package com.rmamedov.deasy.orderservice.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.orderservice.converter.OrderMessageToOrderConverter;
import com.rmamedov.deasy.orderservice.service.ProcessOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProcessOrderKafkaReceiver {

    private final ProcessOrderService processOrderService;

    private final OrderMessageToOrderConverter orderMessageToOrderConverter;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties topicConfiguration;

    public ProcessOrderKafkaReceiver(ProcessOrderService processOrderService,
                                     OrderMessageToOrderConverter orderMessageToOrderConverter,
                                     KafkaReceiverConfigurationProperties receiverProperties,
                                     @Qualifier("successPayedOrdersTopicProp") final TopicConfigurationProperties properties) {

        this.processOrderService = processOrderService;
        this.orderMessageToOrderConverter = orderMessageToOrderConverter;
        this.receiverProperties = receiverProperties;
        this.topicConfiguration = properties;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listenPayedOrders() {
        final var kafkaReceiver =
                new ApplicationKafkaReceiver<OrderMessage>(receiverProperties, List.of(topicConfiguration.getName()));
        kafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final var order = orderMessageToOrderConverter.convert(receiverRecord.value());
                    return processOrderService.updateAndSend(order)
                            .doOnSuccess(checkInfo -> receiverRecord.receiverOffset().acknowledge())
                            .doOnError(ex -> log.error("Exception has occurred: ", ex));
                })
                .subscribe();
    }

}
