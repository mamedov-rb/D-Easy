package com.rmamedov.deasy.etlstarter.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.sender")
    public KafkaSenderProperties kafkaSenderProperties() {
        return new KafkaSenderProperties();
    }

}
