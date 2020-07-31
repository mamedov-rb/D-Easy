package com.rmamedov.deasy.restaurantservice.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-menu-orders-topic")
    public TopicProperties checkedMenuTopicProp() {
        return new TopicProperties();
    }

}
