package com.rmamedov.deasy.orderservice.repository;

import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {

    Mono<Order> findByIdAndCheckStatusesInAndPaymentStatus(String Id, Set<String> statuses, String paymentStatus);

}
