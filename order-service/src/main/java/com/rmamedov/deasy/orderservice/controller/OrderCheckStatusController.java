package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.orderservice.converter.OrderCheckStatusToOrderCheckStatusResponseConverter;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckStatusResponse;
import com.rmamedov.deasy.orderservice.service.OrderCheckStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/order-status")
@RequiredArgsConstructor
public class OrderCheckStatusController {

    private final OrderCheckStatusService checkStatusService;

    private final OrderCheckStatusToOrderCheckStatusResponseConverter toResponseConverter;

    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderCheckStatusResponse> all() {
        return checkStatusService
                .findAll()
                .map(toResponseConverter::convert);
    }

}
