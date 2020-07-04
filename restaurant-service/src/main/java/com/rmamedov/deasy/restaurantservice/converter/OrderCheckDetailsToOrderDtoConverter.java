package com.rmamedov.deasy.restaurantservice.converter;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.restaurantservice.model.OrderRestaurantCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderDtoConverter {

    @Mapping(target = "id", source = "orderId")
    OrderDto convert(OrderRestaurantCheckDetails source);

}
