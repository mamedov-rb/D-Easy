package com.rmamedov.deasy.etlstarter.persistence;

import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface OrderEtlPersistenceService {

    Mono<OrderMessage> findByOrderId(final String orderId);

    Mono<OrderMessage> checkAndSave(final Mono<OrderMessage> orderMessage);

}
