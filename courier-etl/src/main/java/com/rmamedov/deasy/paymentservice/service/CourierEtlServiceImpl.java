package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.application.model.CheckStatus;
import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import com.rmamedov.deasy.etlstarter.service.OrderEtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierEtlServiceImpl implements OrderEtlService {

    @Override
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessageMono) {
        final String successDescription = "Courier is available.";
        final String failedDescription = "Courier is unavailable.";
        return orderMessageMono
                .map(orderMessage -> updateStatus(orderMessage, successDescription));
    }

    private OrderMessage updateStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.COURIER_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Courier checked with result: '{}'.", checkDetails);
        return orderMessage;
    }

    private boolean courierIsAvailable() {
        return true; // TODO 2020-03-22 rustammamedov: Do real check using google library;
    }

}
