package com.rmamedov.deasy.etlstarter.config.hazelcast;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "hazelcast.cache")
    public HazelcastProperties addressHazelcastProperties() {
        return new HazelcastProperties();
    }

}
