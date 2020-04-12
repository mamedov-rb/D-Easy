package com.rmamedov.deasy.restorauntservice.converter;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restorauntservice.model.OrderRestorauntCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    OrderMessage convert(OrderRestorauntCheckDetails source);

}
