package com.rmamedov.deasy.orderservice.config;

import com.rmamedov.deasy.orderservice.config.properties.TopicConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfigurationPropertiesBeanConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.in-progress-orders-topic")
    public TopicConfigurationProperties inProgressOrdersTopicProp() {
        return new TopicConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-addresses-topic")
    public TopicConfigurationProperties checkedAddressesTopicProp() {
        return new TopicConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-couriers-topic")
    public TopicConfigurationProperties checkedCouriersTopicProp() {
        return new TopicConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-menu-orders-topic")
    public TopicConfigurationProperties checkedMenuOrdersTopicProp() {
        return new TopicConfigurationProperties();
    }

}
