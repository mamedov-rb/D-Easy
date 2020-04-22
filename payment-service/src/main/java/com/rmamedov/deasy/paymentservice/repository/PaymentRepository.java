package com.rmamedov.deasy.paymentservice.repository;

import com.rmamedov.deasy.paymentservice.model.repository.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ReactiveCrudRepository<Payment, String> {
}
