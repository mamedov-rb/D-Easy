package com.rmamedov.deasy.kafkastarter.receiver;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class ApplicationKafkaReceiver {

    @Bean
    public ReceiverOptions<String, OrderMessage> kafkaReceiver(final KafkaReceiverConfigurationProperties receiverConfig) {
        final var receiverProps = new HashMap<String, Object>();
        receiverProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, receiverConfig.getBootstrapServers());
        receiverProps.put(ConsumerConfig.GROUP_ID_CONFIG, receiverConfig.getGroupId());
        receiverProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        receiverProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        receiverProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return ReceiverOptions.create(receiverProps);
    }

}
