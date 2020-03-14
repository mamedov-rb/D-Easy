package com.rmamedov.deasy.orderservice.config;

import com.rmamedov.deasy.orderservice.config.properties.KafkaSenderConfigurationProperties;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public KafkaSender<String, OrderMessage> inProgressOrdersSender(final KafkaSenderConfigurationProperties properties) {
        final var producerProps = new HashMap<String, Object>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        final var senderOptions = SenderOptions.<String, OrderMessage>create(producerProps);
        return KafkaSender.create(senderOptions);
    }

}
