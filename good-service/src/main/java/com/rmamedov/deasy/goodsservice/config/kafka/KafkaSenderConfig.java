package com.rmamedov.deasy.goodsservice.config.kafka;

import com.rmamedov.deasy.goodsservice.model.dto.GoodClickMessage;
import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSenderConfig {

    @Bean
    public ApplicationKafkaSender<GoodClickMessage> checkedAddressSender(@Qualifier("goodClickedTopicProps") final TopicProperties topicProperties,
                                                                         final KafkaSenderProperties senderProperties) {

        return new ApplicationKafkaSender<>(topicProperties, senderProperties);
    }

}
