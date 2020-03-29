package com.rmamedov.deasy.converter;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderConverter {

    Order convert(OrderMessage source);

}
