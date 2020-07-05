package com.rmamedov.deasy.restaurantetl.repository;

import com.rmamedov.deasy.restaurantetl.model.OrderRestaurantCheckDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends ReactiveCrudRepository<OrderRestaurantCheckDetails, String> {

//    Mono<Boolean> existsByOrderId(final String orderId);

}
