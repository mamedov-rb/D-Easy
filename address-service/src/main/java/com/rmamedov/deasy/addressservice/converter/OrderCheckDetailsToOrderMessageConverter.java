package com.rmamedov.deasy.addressservice.converter;

import com.rmamedov.deasy.addressservice.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    OrderMessage convert(OrderAddressCheckDetails source);

}
