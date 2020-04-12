package com.rmamedov.deasy.restorauntservice.repository;

import com.rmamedov.deasy.restorauntservice.model.OrderRestorauntCheckDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends ReactiveCrudRepository<OrderRestorauntCheckDetails, String> {

//    Mono<Boolean> existsByOrderId(final String orderId);

}
