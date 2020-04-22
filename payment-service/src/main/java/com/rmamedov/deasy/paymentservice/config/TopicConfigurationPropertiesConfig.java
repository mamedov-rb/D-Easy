package com.rmamedov.deasy.paymentservice.config;

import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfigurationPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.success-payed-orders-topic")
    public TopicConfigurationProperties successPayedOrdersTopicProp() {
        return new TopicConfigurationProperties();
    }

}
