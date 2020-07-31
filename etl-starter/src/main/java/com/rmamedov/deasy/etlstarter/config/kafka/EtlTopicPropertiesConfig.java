package com.rmamedov.deasy.etlstarter.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtlTopicPropertiesConfig {

    @Bean
    @ConditionalOnProperty(prefix = "kafka.topics.new-orders-topic", value = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "kafka.topics.new-orders-topic")
    public TopicProperties newOrdersTopicProp() {
        return new TopicProperties();
    }

}
