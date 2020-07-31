package com.rmamedov.deasy.restaurantservice.converter;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuPositionToMenuPositionDTOConverter {

    MenuPositionDTO convert(MenuPosition source);

}
