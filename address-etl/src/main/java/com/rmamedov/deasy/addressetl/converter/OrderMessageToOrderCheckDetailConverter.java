package com.rmamedov.deasy.addressetl.converter;

import com.rmamedov.deasy.addressetl.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "originCreated", source = "created")
    OrderAddressCheckDetails convert(OrderMessage source);

}
