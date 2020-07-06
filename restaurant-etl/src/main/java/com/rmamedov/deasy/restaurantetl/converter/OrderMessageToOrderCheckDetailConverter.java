package com.rmamedov.deasy.restaurantetl.converter;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restaurantetl.model.OrderRestaurantCheckDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMessageToOrderCheckDetailConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "originCreated", source = "created")
    OrderRestaurantCheckDetails convert(OrderMessage source);

}
