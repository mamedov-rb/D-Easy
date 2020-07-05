package com.rmamedov.deasy.courieretl.repository;

import com.rmamedov.deasy.courieretl.model.OrderCourierCheckDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends ReactiveCrudRepository<OrderCourierCheckDetails, String> {

//    Mono<Boolean> existsByOrderId(final String orderId);

}
