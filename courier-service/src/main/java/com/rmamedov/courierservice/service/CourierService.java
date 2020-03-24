package com.rmamedov.courierservice.service;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierService {

    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessage) {
        return orderMessage.doOnNext(this::check);
    }

    private void check(final OrderMessage orderMessage) {
        final String successDescription = "Courier is available.";
        final String failedDescription = "Courier is unavailable.";
        if (courierIsAvailable()) {
            updateCheckStatus(orderMessage, successDescription);
        } else {
            updateCheckStatus(orderMessage, failedDescription);
        }
    }

    private void updateCheckStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.COURIER_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Courier checked with result: '{}'.", checkDetails);
    }

    private boolean courierIsAvailable() {
        return true; // TODO 2020-03-22 rustammamedov: Do real check using google library;
    }

}
