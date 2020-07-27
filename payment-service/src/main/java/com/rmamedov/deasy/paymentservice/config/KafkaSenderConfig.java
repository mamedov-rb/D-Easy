package com.rmamedov.deasy.paymentservice.config;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender newOrdersSender(final TopicConfigurationProperties topicProperties,
                                                  final KafkaSenderProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

}
