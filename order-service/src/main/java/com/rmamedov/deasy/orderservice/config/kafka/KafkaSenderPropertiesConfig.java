package com.rmamedov.deasy.orderservice.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.sender")
    public KafkaSenderProperties addressKafkaSenderConfig() {
        return new KafkaSenderProperties();
    }

//    @Bean
//    @ConditionalOnProperty(prefix = "kafka.sender.courier", value = "enabled", havingValue = "true")
//    @ConfigurationProperties(prefix = "kafka.sender.courier")
//    public KafkaSenderProperties courierKafkaSenderConfig() {
//        return new KafkaSenderProperties();
//    }

}
