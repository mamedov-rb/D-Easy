package com.rmamedov.deasy.orderservice.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.new-orders-topic")
    public TopicProperties newOrdersTopicProp() {
        return new TopicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-addresses-topic")
    public TopicProperties checkedAddressesTopicProp() {
        return new TopicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-couriers-topic")
    public TopicProperties checkedCouriersTopicProp() {
        return new TopicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-menu-orders-topic")
    public TopicProperties checkedMenuOrdersTopicProp() {
        return new TopicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.success-payed-orders-topic")
    public TopicProperties successPayedOrdersTopicProp() {
        return new TopicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.ready-to-cook-topic")
    public TopicProperties readyToCookTopicProp() {
        return new TopicProperties();
    }

}
