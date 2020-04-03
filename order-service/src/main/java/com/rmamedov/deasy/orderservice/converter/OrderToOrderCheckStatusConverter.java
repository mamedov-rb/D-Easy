package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.repository.Order;
import com.rmamedov.deasy.orderservice.model.repository.OrderCheckStatusResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderToOrderCheckStatusConverter {

    @Mapping(target = "id", ignore = true)
    OrderCheckStatusResult convert(Order source);

}
