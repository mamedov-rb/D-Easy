package com.rmamedov.deasy.courieretl.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender checkedCourierSender(@Qualifier("checkedCourierTopicProp") final TopicConfigurationProperties topicProperties,
                                                      final KafkaSenderProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

}
