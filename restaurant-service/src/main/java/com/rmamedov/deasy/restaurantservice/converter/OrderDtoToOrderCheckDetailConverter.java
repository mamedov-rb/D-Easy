package com.rmamedov.deasy.restaurantservice.converter;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.restaurantservice.model.OrderRestaurantCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    OrderRestaurantCheckDetails convert(OrderDto source);

}
