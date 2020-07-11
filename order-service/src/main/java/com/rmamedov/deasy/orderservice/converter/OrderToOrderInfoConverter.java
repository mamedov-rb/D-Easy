package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.model.controller.OrderInfo;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderInfoConverter {

    OrderInfo convert(Order source);

}
