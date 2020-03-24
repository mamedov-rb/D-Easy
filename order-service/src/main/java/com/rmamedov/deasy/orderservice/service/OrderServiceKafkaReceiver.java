package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.converter.OrderMessageToOrderConverter;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Set;

@Slf4j
@Service
public class OrderServiceKafkaReceiver {

    public static final Set<CheckStatus> FULLY_CHECKED_SET = Set.of(
            CheckStatus.ADDRESSES_CHECKED,
            CheckStatus.ORDER_MENU_CHECKED,
            CheckStatus.COURIER_CHECKED
    );

    private ApplicationKafkaReceiver applicationKafkaReceiver;

    private final OrderService orderService;

    private final OrderCheckStatusService notificationService;

    private final OrderMessageToOrderConverter orderMessageToOrderConverter;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final TopicConfigurationProperties checkedAddressesTopicProperties;

    public OrderServiceKafkaReceiver(OrderService orderService,
                                     OrderCheckStatusService notificationService,
                                     OrderMessageToOrderConverter orderMessageToOrderConverter,
                                     KafkaReceiverConfigurationProperties receiverProperties,
                                     @Qualifier("checkedAddressesTopicProp") TopicConfigurationProperties checkedAddressesTopicProperties) {

        this.orderService = orderService;
        this.notificationService = notificationService;
        this.orderMessageToOrderConverter = orderMessageToOrderConverter;
        this.receiverProperties = receiverProperties;
        this.checkedAddressesTopicProperties = checkedAddressesTopicProperties;
    }

    @PostConstruct
    public void init() {
        applicationKafkaReceiver = new ApplicationKafkaReceiver(receiverProperties, checkedAddressesTopicProperties);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listenCheckedAddresses() {
        applicationKafkaReceiver.receive()
                .map(receiverRecord -> orderMessageToOrderConverter.convert(receiverRecord.value()))
                .doOnNext(order -> {
                    if (order.getCheckStatuses().containsAll(FULLY_CHECKED_SET)) {
                        order.setCheckStatuses(Set.of(CheckStatus.FULLY_CHECKED));
                    }
                })
                .flatMap(orderService::save)
                .flatMap(notificationService::save)
                .subscribe();
    }

}
