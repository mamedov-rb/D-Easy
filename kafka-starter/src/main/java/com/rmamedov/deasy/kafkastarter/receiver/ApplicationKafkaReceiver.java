package com.rmamedov.deasy.kafkastarter.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
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
public class ApplicationKafkaReceiver {

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final List<String> topics;

    public Flux<ReceiverRecord<String, OrderMessage>> receive() {
        final var subscription = receiverOptions().subscription(topics);
        return KafkaReceiver.create(subscription)
                .receive()
                .log();
    }

    private ReceiverOptions<String, OrderMessage> receiverOptions() {
        final var receiverProps = new HashMap<String, Object>();
        receiverProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, receiverProperties.getBootstrapServers());
        receiverProps.put(ConsumerConfig.GROUP_ID_CONFIG, receiverProperties.getGroupId());
        receiverProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        receiverProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        receiverProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return ReceiverOptions.create(receiverProps);
    }

}
