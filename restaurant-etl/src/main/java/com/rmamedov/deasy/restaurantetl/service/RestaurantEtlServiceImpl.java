package com.rmamedov.deasy.restaurantetl.service;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.restaurantetl.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.restaurantetl.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.restaurantetl.model.OrderRestaurantCheckDetails;
import com.rmamedov.deasy.restaurantetl.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantEtlServiceImpl implements RestaurantEtlService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toOrderMessageConverter;

    @Override
    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> OrderMessage) {
        return OrderMessage.map(this::check)
                .flatMap(checkedOrderMessage -> {
                    final OrderRestaurantCheckDetails checkDetails = detailConverter.convert(checkedOrderMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved restaurant details after check: '{}'", savedDetails))
                            .map(toOrderMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage OrderMessage) {
        final String successDescription = "All menu can be cocked, it might took 30min.";
        final String failedDescription = "All menu can be cocked, it might took 30min.";
        if (allPositionsCanBeCooked()) {
            updateCheckStatus(OrderMessage, successDescription);
        } else {
            updateCheckStatus(OrderMessage, failedDescription);
        }
        return OrderMessage;
    }

    private void updateCheckStatus(final OrderMessage OrderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ORDER_MENU_CHECKED;
        OrderMessage.getCheckStatuses().add(checkStatus);
        OrderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Menu checked with result: '{}'.", checkDetails);
    }

    private boolean allPositionsCanBeCooked() {
        return true;  // TODO 2020-03-24 rustammamedov: Do real check by each position.
    }

}
