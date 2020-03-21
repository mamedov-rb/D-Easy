package com.rmamedov.deasy.model.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum DeliveryStatus {

    NEW(Collections.EMPTY_SET),
    FULLY_APPROVED(Set.of("ADDRESSES_APPROVED", "RESTORAUNT_APPROVED", "COURIER_APPROVED"));

    @Getter
    private final Set<String> statuses;

}
