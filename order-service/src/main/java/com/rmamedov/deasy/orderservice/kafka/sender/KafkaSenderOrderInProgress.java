package com.rmamedov.deasy.orderservice.kafka.sender;

import com.rmamedov.deasy.orderservice.config.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Component
public class KafkaSenderOrderInProgress {

    private final TopicConfigurationProperties topicProperties;

    private final KafkaSender<String, OrderMessage> kafkaSenderInProgressOrder;

    public KafkaSenderOrderInProgress(@Qualifier("inProgressOrdersTopicProp") TopicConfigurationProperties topicProperties,
                                      KafkaSender<String, OrderMessage> kafkaSenderInProgressOrder) {

        this.topicProperties = topicProperties;
        this.kafkaSenderInProgressOrder = kafkaSenderInProgressOrder;
    }

    public void send(final OrderMessage orderMessage) {
        kafkaSenderInProgressOrder
                .send(prepareSenderRecord(orderMessage))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .doOnNext(result -> log.info("Sent message: '{}', CorrelationId: '{}'", orderMessage, result.correlationMetadata()))
                .subscribe();
    }

    private Mono<SenderRecord<String, OrderMessage, String>> prepareSenderRecord(final OrderMessage order) {
        return Mono.just(SenderRecord.create(new ProducerRecord<>(topicProperties.getName(), order.getId(), order), order.getId()));
    }

}
