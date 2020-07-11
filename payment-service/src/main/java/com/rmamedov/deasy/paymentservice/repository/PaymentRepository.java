package com.rmamedov.deasy.paymentservice.repository;

import com.rmamedov.deasy.paymentservice.model.repository.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentRepository extends ReactiveCrudRepository<Payment, String> {

    Mono<Boolean> existsByOrderId(String orderId);

}
