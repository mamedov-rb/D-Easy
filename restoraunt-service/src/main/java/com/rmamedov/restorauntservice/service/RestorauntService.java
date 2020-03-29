package com.rmamedov.restorauntservice.service;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestorauntService {

    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessage) {
        return orderMessage.doOnNext(this::check);
    }

    private void check(final OrderMessage orderMessage) {
        final String successDescription = "All menu can be cocked, it might took 30min.";
        final String failedDescription = "All menu can be cocked, it might took 30min.";
        if (allPositionsCanBeCooked()) {
            updateCheckStatus(orderMessage, successDescription);
        } else {
            updateCheckStatus(orderMessage, failedDescription);
        }
    }

    private void updateCheckStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ORDER_MENU_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Menu checked with result: '{}'.", checkDetails);
    }

    private boolean allPositionsCanBeCooked() {
        return true;  // TODO 2020-03-24 rustammamedov: Do real check by each position.
    }

}
