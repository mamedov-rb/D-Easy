package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.controller.OrderCheckStatusResponse;
import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderCheckStatusToOrderCheckStatusResponseConverter {

    OrderCheckStatusResponse convert(OrderCheckStatus source);

}

