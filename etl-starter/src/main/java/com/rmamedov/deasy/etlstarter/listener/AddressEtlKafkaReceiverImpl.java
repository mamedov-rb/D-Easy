package com.rmamedov.deasy.etlstarter.listener;

import com.rmamedov.deasy.etlstarter.persistence.OrderEtlPersistenceService;
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
public class AddressEtlKafkaReceiverImpl implements AddressEtlKafkaReceiver {

    private final OrderEtlPersistenceService persistenceService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties newOrdersTopicProps;

    public AddressEtlKafkaReceiverImpl(OrderEtlPersistenceService persistenceService,
                                       ApplicationKafkaSender applicationKafkaSender,
                                       KafkaReceiverConfigurationProperties receiverProperties,
                                       @Qualifier("newOrdersTopicProp") TopicConfigurationProperties newOrdersTopicProps) {

        this.persistenceService = persistenceService;
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
                .flatMap(receiverRecord -> {
                    final OrderMessage orderMessage = receiverRecord.value();
                    return persistenceService.findByOrderId(orderMessage.getId())
                            .switchIfEmpty(persistenceService.checkAndSave(Mono.just(orderMessage))
                                    .doOnNext(checkResult -> {
                                        applicationKafkaSender.send(checkResult);
                                        receiverRecord.receiverOffset().acknowledge();
                                    }));
                })
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .subscribe();
    }

}
