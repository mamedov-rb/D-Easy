package com.rmamedov.courierservice.config;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender checkedCouriersSender(@Qualifier("checkedCouriersTopicProp") final TopicConfigurationProperties topicProperties,
                                                        final KafkaSenderConfigurationProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

}
