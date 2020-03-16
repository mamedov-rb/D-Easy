package com.rmamedov.addressservice.receiver;

import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;

@Slf4j
@Component
public class AddressServiceKafkaReceiver {

    private final TopicConfigurationProperties topicConfig;

    private final ReceiverOptions<String, OrderMessage> receiverOptions;

    public AddressServiceKafkaReceiver(@Qualifier("inProgressOrdersTopicProp") TopicConfigurationProperties topicConfig,
                                       ReceiverOptions<String, OrderMessage> receiverOptions) {

        this.topicConfig = topicConfig;
        this.receiverOptions = receiverOptions;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var subscription = receiverOptions.subscription(List.of(this.topicConfig.getName()));
        final var kafkaReceiver = KafkaReceiver.create(subscription);
        kafkaReceiver
                .receive()
                .log()
                .subscribe();
    }

}
