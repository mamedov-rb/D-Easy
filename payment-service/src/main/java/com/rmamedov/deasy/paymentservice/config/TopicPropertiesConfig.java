package com.rmamedov.deasy.paymentservice.config;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.success-payed-orders-topic")
    public TopicProperties successPayedOrdersTopicProp() {
        return new TopicProperties();
    }

}
