package com.rmamedov.deasy.kafkastarter.sender;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class ApplicationKafkaSender {

    private final TopicProperties topicConfig;

    private final KafkaSenderProperties senderConfig;

    public void send(final OrderMessage orderMessage) {
        final var senderRecord = SenderRecord.create(
                new ProducerRecord<>(topicConfig.getName(), orderMessage.getId(), orderMessage),
                orderMessage.getId()
        );
        kafkaSender()
                .send(Flux.just(senderRecord))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .doOnNext(result -> log.info("Sent -> topic: '{}', OrderMessage: '{}', correlationId: '{}'",
                        topicConfig.getName(), orderMessage, result.correlationMetadata()))
                .subscribe();
    }

    private KafkaSender<String, OrderMessage> kafkaSender() {
        final var senderProps = new HashMap<String, Object>();
        senderProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, senderConfig.getBootstrapServers());
        senderProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        senderProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        final var senderOptions = SenderOptions.<String, OrderMessage>create(senderProps);
        return KafkaSender.create(senderOptions);
    }

}
