package com.rmamedov.deasy.orderservice.repository;

import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCheckStatusRepository extends ReactiveMongoRepository<OrderCheckStatus, String> {
}
