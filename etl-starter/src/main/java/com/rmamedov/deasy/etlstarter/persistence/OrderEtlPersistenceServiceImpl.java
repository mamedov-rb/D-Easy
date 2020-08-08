package com.rmamedov.deasy.etlstarter.persistence;

import com.hazelcast.map.IMap;
import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import com.rmamedov.deasy.etlstarter.service.OrderEtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderEtlPersistenceServiceImpl implements OrderEtlPersistenceService {

    private final IMap<String, OrderMessage> cache;

    private final OrderEtlService orderEtlService;

    @Override
    public Mono<OrderMessage> findByOrderId(final String orderId) {
        return Mono.fromCallable(() -> cache.get(orderId));
    }

    @Override
    public Mono<OrderMessage> checkAndSave(final Mono<OrderMessage> orderMessageMono) {
        return orderEtlService.check(orderMessageMono)
                .doOnNext(checked -> cache.putIfAbsent(checked.getId(), checked));
    }

}
