package com.rmamedov.deasy.kafkastarter.sender;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
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
public class ApplicationKafkaSender<T> {

    private final TopicProperties topicConfig;

    private final KafkaSenderProperties senderConfig;

    public void send(final T message, final String key) {
        final var senderRecord = SenderRecord.create(new ProducerRecord<>(topicConfig.getName(), key, message), key);
        kafkaSender()
                .send(Flux.just(senderRecord))
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .doOnNext(result -> log.info("Sent -> topic: '{}', Message: '{}', correlationId: '{}'",
                        topicConfig.getName(), message, result.correlationMetadata()))
                .subscribe();
    }

    private KafkaSender<String, T> kafkaSender() {
        final var senderProps = new HashMap<String, Object>();
        senderProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, senderConfig.getBootstrapServers());
        senderProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        senderProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        final var senderOptions = SenderOptions.<String, T>create(senderProps);
        return KafkaSender.create(senderOptions);
    }

}
