package com.rmamedov.deasy.courierservice.converter;

import com.rmamedov.deasy.courierservice.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    OrderCourierCheckDetails convert(OrderMessage source);

}
