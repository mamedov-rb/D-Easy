package com.rmamedov.deasy.addressservice.repository;

import com.rmamedov.deasy.addressservice.model.OrderAddressCheckDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends ReactiveCrudRepository<OrderAddressCheckDetails, String> {

//    Mono<Boolean> existsByOrderId(final String orderId);

}
