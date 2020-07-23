package com.rmamedov.deasy.addressetl.service;

import com.rmamedov.deasy.addressetl.model.AddressCheckResult;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import reactor.core.publisher.Mono;

public interface AddressEtlService {

    Mono<AddressCheckResult> findByOrderId(final String orderId);

    Mono<AddressCheckResult> checkAndSave(final OrderMessage orderMessage);

}
