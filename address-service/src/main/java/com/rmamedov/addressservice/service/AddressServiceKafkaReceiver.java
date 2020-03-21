package com.rmamedov.addressservice.service;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceKafkaReceiver {

    private final AddressService addressService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties inProgressOrdersTopicProps;

    public AddressServiceKafkaReceiver(AddressService addressService,
                                       ApplicationKafkaSender applicationKafkaSender,
                                       KafkaReceiverConfigurationProperties receiverProperties,
                                       @Qualifier("inProgressOrdersTopicProp") TopicConfigurationProperties inProgressOrdersTopicProps) {

        this.addressService = addressService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.inProgressOrdersTopicProps = inProgressOrdersTopicProps;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver = new ApplicationKafkaReceiver(receiverProperties, inProgressOrdersTopicProps);
        applicationKafkaReceiver.listen()
                .flatMap(receiverRecord -> addressService.check(receiverRecord.value()))
                .flatMap(applicationKafkaSender::send)
                .subscribe();
    }

}
