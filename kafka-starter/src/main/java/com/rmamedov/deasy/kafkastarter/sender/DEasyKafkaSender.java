package com.rmamedov.deasy.kafkastarter.sender;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.topic.TopicConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class DEasyKafkaSender {

    private final TopicConfigurationProperties topicProperties;

    private final KafkaSenderConfigurationProperties senderProperties;

    public void send(final OrderMessage orderMessage) {
        kafkaSender()
                .send(prepareSenderRecord(orderMessage))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .doOnNext(result -> log.info(
                        "Sent -> topic: '{}', message: '{}', correlationId: '{}'",
                        topicProperties.getName(),
                        orderMessage,
                        result.correlationMetadata())
                )
                .subscribe();
    }

    private Mono<SenderRecord<String, OrderMessage, String>> prepareSenderRecord(final OrderMessage order) {
        return Mono.just(SenderRecord.create(new ProducerRecord<>(topicProperties.getName(), order.getId(), order), order.getId()));
    }

    private KafkaSender<String, OrderMessage> kafkaSender() {
        final var producerProps = new HashMap<String, Object>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, senderProperties.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        final var senderOptions = SenderOptions.<String, OrderMessage>create(producerProps);
        return KafkaSender.create(senderOptions);
    }

}
