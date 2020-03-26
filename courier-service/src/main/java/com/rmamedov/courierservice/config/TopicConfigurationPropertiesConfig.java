package com.rmamedov.courierservice.config;

import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfigurationPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.new-orders-topic")
    public TopicConfigurationProperties newOrdersTopicProp() {
        return new TopicConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-couriers-topic")
    public TopicConfigurationProperties checkedCouriersTopicProp() {
        return new TopicConfigurationProperties();
    }

}
