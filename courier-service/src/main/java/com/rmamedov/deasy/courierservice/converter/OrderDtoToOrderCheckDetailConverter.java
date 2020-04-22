package com.rmamedov.deasy.courierservice.converter;

import com.rmamedov.deasy.courierservice.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    OrderCourierCheckDetails convert(OrderDto source);

}
