package com.rmamedov.deasy.orderservice.config;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender newOrdersSender(@Qualifier("newOrdersTopicProp") final TopicConfigurationProperties topicProperties,
                                                  final KafkaSenderConfigurationProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

    @Bean
    public ApplicationKafkaSender readyToCookSender(@Qualifier("readyToCookTopicProp") final TopicConfigurationProperties topicProperties,
                                                  final KafkaSenderConfigurationProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

}
