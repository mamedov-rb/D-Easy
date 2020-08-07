package com.rmamedov.deasy.goodsservice.config.kafka;

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
    public NewTopic goodClickedTopic(@Qualifier("goodClickedTopicProps") final TopicProperties properties) {
        return TopicBuilder.name(properties.getName())
                .partitions(properties.getPartitions())
                .replicas(properties.getReplicas())
                .compact()
                .build();
    }

}
