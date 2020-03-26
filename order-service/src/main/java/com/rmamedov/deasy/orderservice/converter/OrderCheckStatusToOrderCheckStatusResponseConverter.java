package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.controller.OrderCheckStatusResultResponse;
import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatusResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderCheckStatusToOrderCheckStatusResponseConverter {

    OrderCheckStatusResultResponse convert(OrderCheckStatusResult source);

}

