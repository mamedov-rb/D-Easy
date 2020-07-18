package com.rmamedov.deasy.restaurantetl.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restaurantetl.service.RestaurantEtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RestaurantEtlKafkaReceiverImpl implements RestaurantEtlKafkaReceiver {

    private final RestaurantEtlService restaurantEtlService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties newOrdersTopicProps;

    public RestaurantEtlKafkaReceiverImpl(RestaurantEtlService restaurantEtlService,
                                          ApplicationKafkaSender applicationKafkaSender,
                                          KafkaReceiverConfigurationProperties receiverProperties,
                                          @Qualifier("newOrdersTopicProp") TopicConfigurationProperties newOrdersTopicProps) {

        this.restaurantEtlService = restaurantEtlService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.newOrdersTopicProps = newOrdersTopicProps;
    }

    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver =
                new ApplicationKafkaReceiver<OrderMessage>(receiverProperties, List.of(newOrdersTopicProps.getName()));

        applicationKafkaReceiver.receive()
                .flatMap(receiverRecord -> restaurantEtlService.check(Mono.just(receiverRecord.value()))
                        .doOnNext(OrderMessage -> {
                            applicationKafkaSender.send(OrderMessage);
                            receiverRecord.receiverOffset().acknowledge();
                        }))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .subscribe();
    }

}
