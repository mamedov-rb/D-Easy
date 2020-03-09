package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.orderservice.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderCreateRequestToOrderConverter {

//    @Mappings({
//            @Mapping(target="employeeId", source="entity.id"),
//            @Mapping(target="employeeName", source="entity.name")
//    })
    Order convert(OrderCreateRequest createRequest);

}
