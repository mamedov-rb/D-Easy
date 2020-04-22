package com.rmamedov.deasy.restorauntservice.service;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RestorauntServiceKafkaReceiver {

    private final RestorauntService restorauntService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties newOrdersTopicProps;

    public RestorauntServiceKafkaReceiver(RestorauntService restorauntService,
                                          ApplicationKafkaSender applicationKafkaSender,
                                          KafkaReceiverConfigurationProperties receiverProperties,
                                          @Qualifier("newOrdersTopicProp") TopicConfigurationProperties newOrdersTopicProps) {

        this.restorauntService = restorauntService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.newOrdersTopicProps = newOrdersTopicProps;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver =
                new ApplicationKafkaReceiver(receiverProperties, List.of(newOrdersTopicProps.getName()));

        applicationKafkaReceiver.receive()
                .flatMap(receiverRecord -> restorauntService.check(Mono.just(receiverRecord.value()))
                        .doOnNext(orderDto -> {
                            applicationKafkaSender.send(orderDto);
                            receiverRecord.receiverOffset().acknowledge();
                        }))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .subscribe();
    }

}
