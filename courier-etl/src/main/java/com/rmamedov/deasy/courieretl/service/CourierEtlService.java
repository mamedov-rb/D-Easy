package com.rmamedov.deasy.courieretl.service;

import com.rmamedov.deasy.courieretl.converter.OrderCheckDetailsToOrderDtoConverter;
import com.rmamedov.deasy.courieretl.converter.OrderDtoToOrderCheckDetailConverter;
import com.rmamedov.deasy.courieretl.model.OrderCourierCheckDetails;
import com.rmamedov.deasy.courieretl.repository.OrderDetailsRepository;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderDto;
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

    private final OrderDtoToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderDtoConverter toDtoConverter;

    @Transactional
    public Mono<OrderDto> check(final Mono<OrderDto> orderDto) {
        return orderDto.map(this::check)
                .map(this::check)
                .flatMap(checkedDto -> {
                    final OrderCourierCheckDetails checkDetails = detailConverter.convert(checkedDto);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved courier details after check: '{}'", savedDetails))
                            .map(toDtoConverter::convert);
                });
    }

    private OrderDto check(final OrderDto orderDto) {
        final String successDescription = "Courier is available.";
        final String failedDescription = "Courier is unavailable.";
        if (courierIsAvailable()) {
            updateCheckStatus(orderDto, successDescription);
        } else {
            updateCheckStatus(orderDto, failedDescription);
        }
        return orderDto;
    }

    private void updateCheckStatus(final OrderDto orderDto, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.COURIER_CHECKED;
        orderDto.getCheckStatuses().add(checkStatus);
        orderDto.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Courier checked with result: '{}'.", checkDetails);
    }

    private boolean courierIsAvailable() {
        return true; // TODO 2020-03-22 rustammamedov: Do real check using google library;
    }

}
