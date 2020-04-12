package com.rmamedov.deasy.courierservice.service;

import com.rmamedov.deasy.courierservice.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.courierservice.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.courierservice.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.courierservice.repository.OrderDetailsRepository;
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
public class CourierService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toMessageConverter;

    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessage) {
        return orderMessage.map(this::check)
                .map(this::check)
                .flatMap(checkedMessage -> {
                    final OrderCourierCheckDetails checkDetails = detailConverter.convert(checkedMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved courier details after check: '{}'", savedDetails))
                            .map(toMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage orderMessage) {
        final String successDescription = "Courier is available.";
        final String failedDescription = "Courier is unavailable.";
        if (courierIsAvailable()) {
            updateCheckStatus(orderMessage, successDescription);
        } else {
            updateCheckStatus(orderMessage, failedDescription);
        }
        return orderMessage;
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
