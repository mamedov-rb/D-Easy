package com.rmamedov.deasy.restorauntservice.converter;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restorauntservice.model.OrderRestorauntCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    OrderRestorauntCheckDetails convert(OrderMessage source);

}
