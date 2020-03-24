package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.model.repository.Order;
import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderCheckStatusConverter {

    OrderCheckStatus convert(Order source);

}
