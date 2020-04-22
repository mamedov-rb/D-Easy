package com.rmamedov.deasy.paymentservice.converter;

import com.rmamedov.deasy.paymentservice.model.controller.PayResponse;
import com.rmamedov.deasy.paymentservice.model.repository.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentToPayResponseConverter {

    @Mapping(target = "transactionTimestamp", source = "created")
    PayResponse convert(Payment payment);

}
