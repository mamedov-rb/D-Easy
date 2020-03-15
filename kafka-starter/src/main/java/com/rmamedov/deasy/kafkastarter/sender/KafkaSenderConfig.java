package com.rmamedov.deasy.kafkastarter.sender;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.topic.TopicConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public DEasyKafkaSender inProgressOrdersSender(@Qualifier("inProgressOrdersTopicProp") final TopicConfigurationProperties topicProperties,
                                                   final KafkaSenderConfigurationProperties senderProperties) {

        return new DEasyKafkaSender(topicProperties, senderProperties);
    }

    @Bean
    public DEasyKafkaSender checkedAddressSender(@Qualifier("checkedAddressesTopicProp") final TopicConfigurationProperties topicProperties,
                                                   final KafkaSenderConfigurationProperties senderProperties) {

        return new DEasyKafkaSender(topicProperties, senderProperties);
    }

    @Bean
    public DEasyKafkaSender checkedMenuSender(@Qualifier("checkedMenuOrdersTopicProp") final TopicConfigurationProperties topicProperties,
                                                   final KafkaSenderConfigurationProperties senderProperties) {

        return new DEasyKafkaSender(topicProperties, senderProperties);
    }

    @Bean
    public DEasyKafkaSender checkedCouriersSender(@Qualifier("checkedCouriersTopicProp") final TopicConfigurationProperties topicProperties,
                                                   final KafkaSenderConfigurationProperties senderProperties) {

        return new DEasyKafkaSender(topicProperties, senderProperties);
    }

}
