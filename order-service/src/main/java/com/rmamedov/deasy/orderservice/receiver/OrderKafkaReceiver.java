package com.rmamedov.deasy.orderservice.receiver;

import com.rmamedov.deasy.converter.OrderMessageToOrderConverter;
import com.rmamedov.deasy.kafkastarter.properties.KafkaReceiverConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.properties.TopicConfigurationProperties;
import com.rmamedov.deasy.kafkastarter.receiver.ApplicationKafkaReceiver;
import com.rmamedov.deasy.orderservice.config.MongoConfigurationProperties;
import com.rmamedov.deasy.orderservice.service.ProcessOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Duration.ofMillis;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaReceiver {

    private ApplicationKafkaReceiver kafkaReceiver;

    private final ProcessOrderService processOrderService;

    private final OrderMessageToOrderConverter orderMessageToOrderConverter;

    private final KafkaReceiverConfigurationProperties receiverProperties;

    private final MongoConfigurationProperties mongoProperties;

    private final List<TopicConfigurationProperties> topicConfigurationList;

    @PostConstruct
    public void init() {
        final List<String> topicNames = topicConfigurationList.stream()
                .map(TopicConfigurationProperties::getName)
                .filter(name -> !name.contains("new"))
                .collect(Collectors.toList());
        kafkaReceiver = new ApplicationKafkaReceiver(receiverProperties, topicNames);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listenCheckedAddresses() {
        kafkaReceiver.receive()
                .flatMap(receiverRecord -> {
                    final var order = orderMessageToOrderConverter.convert(receiverRecord.value());
                    return processOrderService.updateExistingOrder(order)
                            .retryBackoff(
                                    mongoProperties.getNumRetries(),
                                    ofMillis(mongoProperties.getFirstBackoff()),
                                    ofMillis(mongoProperties.getMaxBackoff())
                            );
                })
                .doOnError(ex -> log.error("Exception has occurred: ", ex))
                .subscribeOn(Schedulers.single())
                .subscribe();
    }

}
