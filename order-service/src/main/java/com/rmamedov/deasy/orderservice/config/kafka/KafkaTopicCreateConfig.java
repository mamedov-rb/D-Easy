package com.rmamedov.deasy.orderservice.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
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
    public NewTopic newOrdersConfigTopic(@Qualifier("newOrdersTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedAddressesConfigTopic(@Qualifier("checkedAddressesTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedCouriersConfigTopic(@Qualifier("checkedCouriersTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic checkedMenuOrdersConfigTopic(@Qualifier("checkedMenuOrdersTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic successPayedOrdersConfigTopic(@Qualifier("successPayedOrdersTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

    @Bean
    public NewTopic readyToCookTopicPropConfigTopic(@Qualifier("readyToCookTopicProp") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

}
