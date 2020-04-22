package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderDtoConverter {

    OrderDto convert(Order order);

}
