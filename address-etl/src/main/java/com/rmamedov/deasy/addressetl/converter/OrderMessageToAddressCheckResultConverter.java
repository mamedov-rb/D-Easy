package com.rmamedov.deasy.addressetl.converter;

import com.rmamedov.deasy.addressetl.model.AddressCheckResult;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToAddressCheckResultConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "originCreated", source = "created")
    @Mapping(target = "orderName", source = "name")
    AddressCheckResult convert(OrderMessage source);

}
