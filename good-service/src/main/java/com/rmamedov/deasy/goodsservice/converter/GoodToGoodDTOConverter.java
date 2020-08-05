package com.rmamedov.deasy.goodsservice.converter;

import com.rmamedov.deasy.goodsservice.model.Good;
import com.rmamedov.deasy.goodsservice.model.dto.GoodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodToGoodDTOConverter {

    GoodDTO convert(Good source);

}
