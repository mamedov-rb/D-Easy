package com.rmamedov.deasy.restaurantservice.service;

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
public class RestaurantEtlServiceImpl implements OrderEtlService {

    @Override
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessageMono) {
        final String successDescription = "All menu can be cocked, it might took 30min.";
        final String failedDescription = "All menu can be cocked, it might took 30min.";
        return orderMessageMono
                .map(orderMessage -> updateStatus(orderMessage, successDescription));
    }

    private OrderMessage updateStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ORDER_MENU_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Menu checked with result: '{}'.", checkDetails);
        return orderMessage;
    }

    private boolean allPositionsCanBeCooked() {
        return true;  // TODO 2020-03-24 rustammamedov: Do real check by each position.
    }

}
