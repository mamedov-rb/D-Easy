//package com.rmamedov.deasy.orderservice.service;
//
//import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
//import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
//import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
//import com.rmamedov.deasy.orderservice.repository.OrderRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Service;
//import reactor.kafka.receiver.ReceiverRecord;
//
//@Slf4j
//@Service
//public class OrderServiceKafkaReceiver {
//
//    private final OrderRepository orderRepository;
//
//    private final KafkaReceiverConfigurationProperties receiverProperties;
//
//    private final TopicConfigurationProperties checkedAddressesTopicProperties;
//
//    private final TopicConfigurationProperties checkedMenuOrdersTopicProperties;
//
//    private final TopicConfigurationProperties checkedCouriersTopicProperties;
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void listenCheckedAddresses() {
//        final var applicationKafkaReceiver =
//                new ApplicationKafkaReceiver(receiverProperties, checkedAddressesTopicProperties);
//        applicationKafkaReceiver.listen()
//                .map(ReceiverRecord::value)
//                .flatMap(orderRepository::save)
//                .subscribe();
//    }
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void listenCheckedMenuOrders() {
//        final var applicationKafkaReceiver =
//                new ApplicationKafkaReceiver(receiverProperties, checkedMenuOrdersTopicProperties);
//        applicationKafkaReceiver.listen()
//                .map(ReceiverRecord::value)
//                .flatMap(orderRepository::save)
//                .subscribe();
//    }
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void listenCheckedCouriers() {
//        final var applicationKafkaReceiver =
//                new ApplicationKafkaReceiver(receiverProperties, checkedCouriersTopicProperties);
//        applicationKafkaReceiver.listen()
//                .map(ReceiverRecord::value)
//                .flatMap(orderRepository::save)
//                .subscribe();
//    }
//
//}
