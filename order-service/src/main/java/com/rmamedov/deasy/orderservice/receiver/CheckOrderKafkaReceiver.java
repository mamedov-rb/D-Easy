package com.rmamedov.deasy.orderservice.receiver;

import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import reactor.core.publisher.Flux;

public interface CheckOrderKafkaReceiver {

    Flux<OrderCheckInfo> listenCheckedOrders();

}
