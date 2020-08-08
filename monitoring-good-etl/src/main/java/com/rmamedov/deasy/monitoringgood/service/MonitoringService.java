package com.rmamedov.deasy.monitoringgood.service;

import com.rmamedov.deasy.application.model.kafka.GoodClickMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MonitoringService {

    @SendTo(Processor.OUTPUT)
    @StreamListener(Processor.INPUT)
    public GoodClickMessage processClickStreamWithInfo(final GoodClickMessage message) {
        log.info("Received message: {}", message);
        return message;
    }

}
