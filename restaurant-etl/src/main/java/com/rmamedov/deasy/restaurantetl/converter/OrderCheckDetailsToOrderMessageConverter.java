package com.rmamedov.deasy.restaurantetl.converter;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restaurantetl.model.OrderRestaurantCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckDetailsToOrderMessageConverter {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "created", ignore = true)
    OrderMessage convert(OrderRestaurantCheckDetails source);

}
