package com.rmamedov.deasy.orderservice.repository;

import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatusResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCheckStatusResultRepository extends ReactiveCrudRepository<OrderCheckStatusResult, String> {
}
