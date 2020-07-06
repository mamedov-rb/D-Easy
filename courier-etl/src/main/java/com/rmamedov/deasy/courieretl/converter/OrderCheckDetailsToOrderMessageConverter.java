package com.rmamedov.deasy.courieretl.converter;

import com.rmamedov.deasy.courieretl.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderMessage convert(OrderCourierCheckDetails source);

}
