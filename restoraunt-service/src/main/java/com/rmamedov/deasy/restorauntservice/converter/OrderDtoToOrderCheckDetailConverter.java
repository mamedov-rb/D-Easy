package com.rmamedov.deasy.restorauntservice.converter;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.restorauntservice.model.OrderRestorauntCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    OrderRestorauntCheckDetails convert(OrderDto source);

}
