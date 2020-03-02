package com.rmamedov.deasy.orderservice.repository;

import com.rmamedov.deasy.orderservice.repository.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
