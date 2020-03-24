package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.model.controller.OrderInfoResponse;
import com.rmamedov.deasy.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderInfoConverter {

    OrderInfoResponse convert(Order source);

}
