package com.rmamedov.deasy.orderservice.repository;

import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
}
