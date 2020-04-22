package com.rmamedov.deasy.restorauntservice.service;

import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.restorauntservice.converter.OrderCheckDetailsToOrderDtoConverter;
import com.rmamedov.deasy.restorauntservice.converter.OrderDtoToOrderCheckDetailConverter;
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

    private final OrderDtoToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderDtoConverter toDtoConverter;

    @Transactional
    public Mono<OrderDto> check(final Mono<OrderDto> orderDto) {
        return orderDto.map(this::check)
                .flatMap(checkedDto -> {
                    final OrderRestorauntCheckDetails checkDetails = detailConverter.convert(checkedDto);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved restoraunt details after check: '{}'", savedDetails))
                            .map(toDtoConverter::convert);
                });
    }

    private OrderDto check(final OrderDto orderDto) {
        final String successDescription = "All menu can be cocked, it might took 30min.";
        final String failedDescription = "All menu can be cocked, it might took 30min.";
        if (allPositionsCanBeCooked()) {
            updateCheckStatus(orderDto, successDescription);
        } else {
            updateCheckStatus(orderDto, failedDescription);
        }
        return orderDto;
    }

    private void updateCheckStatus(final OrderDto orderDto, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ORDER_MENU_CHECKED;
        orderDto.getCheckStatuses().add(checkStatus);
        orderDto.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Menu checked with result: '{}'.", checkDetails);
    }

    private boolean allPositionsCanBeCooked() {
        return true;  // TODO 2020-03-24 rustammamedov: Do real check by each position.
    }

}
