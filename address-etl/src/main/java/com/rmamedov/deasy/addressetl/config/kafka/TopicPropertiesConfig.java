package com.rmamedov.deasy.addressetl.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.topics.checked-addresses-topic")
    public TopicProperties checkedAddressesTopicProp() {
        return new TopicProperties();
    }

}
