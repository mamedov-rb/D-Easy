package com.rmamedov.deasy.addressetl.converter;

import com.rmamedov.deasy.addressetl.model.AddressCheckResult;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressCheckResultToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "name", source = "orderName")
    @Mapping(target = "created", source = "originCreated")
    OrderMessage convert(AddressCheckResult source);

}
