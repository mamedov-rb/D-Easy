package com.rmamedov.deasy.restaurantservice.converter;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.restaurantservice.model.OrderRestaurantCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderDtoConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderDto convert(OrderRestaurantCheckDetails source);

}
