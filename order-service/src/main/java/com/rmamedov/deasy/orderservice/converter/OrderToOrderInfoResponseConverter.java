package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.controller.model.OrderInfoResponse;
import com.rmamedov.deasy.orderservice.repository.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderInfoResponseConverter {

    OrderInfoResponse convert(Order order);

}
