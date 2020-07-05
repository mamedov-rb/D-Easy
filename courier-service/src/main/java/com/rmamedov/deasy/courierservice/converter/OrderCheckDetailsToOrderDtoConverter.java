package com.rmamedov.deasy.courierservice.converter;

import com.rmamedov.deasy.courierservice.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderDtoConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderDto convert(OrderCourierCheckDetails source);

}
