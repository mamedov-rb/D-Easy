package com.rmamedov.deasy.goodsservice.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.monitoring-good-clicked-topic")
    public TopicProperties goodClickedTopicProps() {
        return new TopicProperties();
    }

}
