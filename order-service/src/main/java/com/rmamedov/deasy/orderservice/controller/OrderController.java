package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.controller.OrderInfoResponse;
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverter;
import com.rmamedov.deasy.orderservice.service.OrderService;
import com.rmamedov.deasy.orderservice.service.ProcessOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final ProcessOrderService processOrderService;

    private final OrderToOrderInfoConverter orderToOrderInfoConverter;

    private final OrderCreateRequestToOrderConverter requestToOrderConverter;

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<String> create(@RequestBody @Validated final Mono<OrderCreateRequest> createRequest) {
        return createRequest.map(requestToOrderConverter::convert)
                .flatMap(processOrderService::newOrder);
    }

    @GetMapping(path = "/id/{id}")
    public Mono<OrderInfoResponse> all(@PathVariable final String id) {
        return orderService
                .findById(id)
                .map(orderToOrderInfoConverter::convert);
    }

    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderInfoResponse> all() {
        return orderService
                .findAll()
                .map(orderToOrderInfoConverter::convert);
    }

}
