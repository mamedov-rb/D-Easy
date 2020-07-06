package com.rmamedov.deasy.courieretl.service;

import com.rmamedov.deasy.courieretl.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.courieretl.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.courieretl.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.courieretl.repository.OrderDetailsRepository;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierEtlService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toOrderMessageConverter;

    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> OrderMessage) {
        return OrderMessage.map(this::check)
                .map(this::check)
                .flatMap(checkedOrderMessage -> {
                    final OrderCourierCheckDetails checkDetails = detailConverter.convert(checkedOrderMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved courier details after check: '{}'", savedDetails))
                            .map(toOrderMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage OrderMessage) {
        final String successDescription = "Courier is available.";
        final String failedDescription = "Courier is unavailable.";
        if (courierIsAvailable()) {
            updateCheckStatus(OrderMessage, successDescription);
        } else {
            updateCheckStatus(OrderMessage, failedDescription);
        }
        return OrderMessage;
    }

    private void updateCheckStatus(final OrderMessage OrderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.COURIER_CHECKED;
        OrderMessage.getCheckStatuses().add(checkStatus);
        OrderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Courier checked with result: '{}'.", checkDetails);
    }

    private boolean courierIsAvailable() {
        return true; // TODO 2020-03-22 rustammamedov: Do real check using google library;
    }

}
