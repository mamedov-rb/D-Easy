package com.rmamedov.deasy.model.converter;

import com.rmamedov.deasy.model.controller.OrderInfoResponse;
import com.rmamedov.deasy.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderToOrderInfoConverter {

    OrderInfoResponse convert(Order source);

}
