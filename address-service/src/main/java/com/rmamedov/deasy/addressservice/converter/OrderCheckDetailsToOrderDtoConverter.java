package com.rmamedov.deasy.addressservice.converter;

import com.rmamedov.deasy.addressservice.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderDtoConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderDto convert(OrderAddressCheckDetails source);

}
