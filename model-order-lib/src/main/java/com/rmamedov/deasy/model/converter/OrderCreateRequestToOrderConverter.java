package com.rmamedov.deasy.model.converter;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderCreateRequestToOrderConverter {

    Order convert(OrderCreateRequest createRequest);

}
