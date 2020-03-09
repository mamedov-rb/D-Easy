package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.kafka.OrderMessage;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderConverter {

    Order convert(OrderMessage order);

}
