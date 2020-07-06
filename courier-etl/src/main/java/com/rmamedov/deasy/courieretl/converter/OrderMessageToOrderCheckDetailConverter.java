package com.rmamedov.deasy.courieretl.converter;

import com.rmamedov.deasy.courieretl.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "originCreated", source = "created")
    OrderCourierCheckDetails convert(OrderMessage source);

}
