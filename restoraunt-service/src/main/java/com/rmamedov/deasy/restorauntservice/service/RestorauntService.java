package com.rmamedov.deasy.restorauntservice.service;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restorauntservice.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.restorauntservice.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.restorauntservice.model.OrderRestorauntCheckDetails;
import com.rmamedov.deasy.restorauntservice.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestorauntService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toMessageConverter;

    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessage) {
        return orderMessage.map(this::check)
                .flatMap(checkedMessage -> {
                    final OrderRestorauntCheckDetails checkDetails = detailConverter.convert(checkedMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved restoraunt details after check: '{}'", savedDetails))
                            .map(toMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage orderMessage) {
        final String successDescription = "All menu can be cocked, it might took 30min.";
        final String failedDescription = "All menu can be cocked, it might took 30min.";
        if (allPositionsCanBeCooked()) {
            updateCheckStatus(orderMessage, successDescription);
        } else {
            updateCheckStatus(orderMessage, failedDescription);
        }
        return orderMessage;
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
