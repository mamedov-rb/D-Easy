package com.rmamedov.deasy.model.converter;

import com.rmamedov.deasy.model.controller.OrderInfoResponse;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderInfoConverter {

    OrderInfoResponse convert(OrderMessage source);

}
