package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.controller.OrderStatusInfo;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderToOrderStatusInfoConverter {

    @Mapping(target = "orderId", source = "id")
    OrderStatusInfo convert(Order source);

}
