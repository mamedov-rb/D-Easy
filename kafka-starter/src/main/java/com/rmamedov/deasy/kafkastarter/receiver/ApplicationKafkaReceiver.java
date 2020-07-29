package com.rmamedov.deasy.kafkastarter.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class ApplicationKafkaReceiver<T> {

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final List<String> topics;

    public Flux<ReceiverRecord<String, T>> receive() {
        final var subscription = receiverOptions().subscription(topics);
        return KafkaReceiver.create(subscription)
                .receive()
                .log();
    }

    private ReceiverOptions<String, T> receiverOptions() {
        final var receiverProps = new HashMap<String, Object>();
        receiverProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, receiverProperties.getBootstrapServers());
        receiverProps.put(ConsumerConfig.GROUP_ID_CONFIG, receiverProperties.getGroupId());
        receiverProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        receiverProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        receiverProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        receiverProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        receiverProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        receiverProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.rmamedov.deasy.model.kafka.OrderMessage");
        return ReceiverOptions.create(receiverProps);
    }

}
