package com.rmamedov.deasy.addressetl.converter;

import com.rmamedov.deasy.addressetl.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderMessage convert(OrderAddressCheckDetails source);

}
