package com.rmamedov.deasy.orderservice.config.topic;

import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Profile("!TEST")
@Configuration
@RequiredArgsConstructor
public class KafkaTopicCreateConfig {

    @Bean
    public NewTopic newOrdersConfigTopic(@Qualifier("newOrdersTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedAddressesConfigTopic(@Qualifier("checkedAddressesTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedCouriersConfigTopic(@Qualifier("checkedCouriersTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedMenuOrdersConfigTopic(@Qualifier("checkedMenuOrdersTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic successPayedOrdersConfigTopic(@Qualifier("successPayedOrdersTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic readyToCookTopicPropConfigTopic(@Qualifier("readyToCookTopicProp") final TopicConfigurationProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

}
