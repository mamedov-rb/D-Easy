package com.rmamedov.deasy.addressetl.receiver;

import com.rmamedov.deasy.addressetl.converter.AddressCheckResultToOrderMessageConverter;
import com.rmamedov.deasy.addressetl.service.AddressEtlService;
import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressEtlKafkaReceiverImpl implements AddressEtlKafkaReceiver {

    private final AddressEtlService addressEtlService;

    private final ApplicationKafkaSender applicationKafkaSender;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final AddressCheckResultToOrderMessageConverter toOrderMessageConverter;

    private final TopicConfigurationProperties newOrdersTopicProps;

    public AddressEtlKafkaReceiverImpl(AddressEtlService addressEtlService,
                                       ApplicationKafkaSender applicationKafkaSender,
                                       KafkaReceiverConfigurationProperties receiverProperties,
                                       AddressCheckResultToOrderMessageConverter toOrderMessageConverter,
                                       @Qualifier("newOrdersTopicProp") TopicConfigurationProperties newOrdersTopicProps) {

        this.addressEtlService = addressEtlService;
        this.applicationKafkaSender = applicationKafkaSender;
        this.receiverProperties = receiverProperties;
        this.toOrderMessageConverter = toOrderMessageConverter;
        this.newOrdersTopicProps = newOrdersTopicProps;
    }

    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void listen() {
        final var applicationKafkaReceiver =
                new ApplicationKafkaReceiver<OrderMessage>(receiverProperties, List.of(newOrdersTopicProps.getName()));

        applicationKafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final OrderMessage orderMessage = receiverRecord.value();
                    return addressEtlService.findByOrderId(orderMessage.getId())
                            .switchIfEmpty(addressEtlService.checkAndSave(orderMessage))
                            .doOnNext(checkResult -> {
                                final OrderMessage checkedMessage = toOrderMessageConverter.convert(checkResult);
                                applicationKafkaSender.send(checkedMessage);
                                receiverRecord.receiverOffset().acknowledge();
                            });
                })
                .doOnError(err -> log.error("Exception has occurred: ", err))
                .subscribe();
    }

}
