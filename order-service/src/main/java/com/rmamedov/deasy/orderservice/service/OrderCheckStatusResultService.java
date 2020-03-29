package com.rmamedov.deasy.orderservice.service;

import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatusResult;
import com.rmamedov.deasy.orderservice.repository.OrderCheckStatusResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCheckStatusResultService {

    private final OrderCheckStatusResultRepository orderCheckStatusResultRepository;

    @Transactional
    public Mono<OrderCheckStatusResult> save(final OrderCheckStatusResult checkStatusResult) {
        return orderCheckStatusResultRepository.save(checkStatusResult);
    }

    @Transactional(readOnly = true)
    public Flux<OrderCheckStatusResult> findAll() {
        return orderCheckStatusResultRepository.findAll();
    }

}
