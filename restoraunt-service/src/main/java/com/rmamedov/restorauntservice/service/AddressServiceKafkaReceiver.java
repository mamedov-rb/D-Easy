package com.rmamedov.restorauntservice.service;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressServiceKafkaReceiver {

    private final RestorauntService restorauntService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties inProgressOrdersTopicProps;

    public AddressServiceKafkaReceiver(RestorauntService restorauntService,
                                       ApplicationKafkaSender applicationKafkaSender,
                                       KafkaReceiverConfigurationProperties receiverProperties,
                                       @Qualifier("inProgressOrdersTopicProp") TopicConfigurationProperties inProgressOrdersTopicProps) {

        this.restorauntService = restorauntService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.inProgressOrdersTopicProps = inProgressOrdersTopicProps;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver = new ApplicationKafkaReceiver(receiverProperties, inProgressOrdersTopicProps);
        applicationKafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final Mono<OrderMessage> orderMessage = Mono.just(receiverRecord.value());
                    return restorauntService.check(orderMessage);
                })
                .doOnNext(applicationKafkaSender::send)
                .subscribe();
    }

}
