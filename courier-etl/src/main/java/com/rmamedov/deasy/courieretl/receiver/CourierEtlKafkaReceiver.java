package com.rmamedov.deasy.courieretl.receiver;

import com.rmamedov.deasy.courieretl.service.CourierEtlService;
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

import java.util.List;

@Slf4j
@Service
public class CourierEtlKafkaReceiver {

    private final CourierEtlService courierEtlService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties newOrdersTopicProps;

    public CourierEtlKafkaReceiver(CourierEtlService courierEtlService,
                                   ApplicationKafkaSender applicationKafkaSender,
                                   KafkaReceiverConfigurationProperties receiverProperties,
                                   @Qualifier("newOrdersTopicProp") TopicConfigurationProperties newOrdersTopicProps) {

        this.courierEtlService = courierEtlService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.newOrdersTopicProps = newOrdersTopicProps;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver =
                new ApplicationKafkaReceiver<OrderMessage>(receiverProperties, List.of(newOrdersTopicProps.getName()));

        applicationKafkaReceiver.receive()
                .flatMap(receiverRecord -> courierEtlService.check(Mono.just(receiverRecord.value()))
                        .doOnNext(OrderMessage -> {
                            applicationKafkaSender.send(OrderMessage);
                            receiverRecord.receiverOffset().acknowledge();
                        }))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .subscribe();
    }

}
