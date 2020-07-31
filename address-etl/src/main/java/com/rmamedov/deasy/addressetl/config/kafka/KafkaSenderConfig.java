package com.rmamedov.deasy.addressetl.config.kafka;

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender checkedAddressSender(@Qualifier("checkedAddressesTopicProp") final TopicProperties topicProperties,
                                                       final KafkaSenderProperties senderProperties) {

        return new ApplicationKafkaSender(topicProperties, senderProperties);
    }

}
