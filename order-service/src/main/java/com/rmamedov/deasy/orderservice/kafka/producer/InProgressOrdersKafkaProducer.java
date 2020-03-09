package com.rmamedov.deasy.orderservice.kafka.producer;

import com.rmamedov.deasy.orderservice.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InProgressOrdersKafkaProducer {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void send(final String topic,
                     final String key,
                     final OrderMessage data) {

        log.info("Send message: topic '{}', key '{}', value '{}'", topic, key, data);
        kafkaTemplate.send(topic, key, data);
    }

}
