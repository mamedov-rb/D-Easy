package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.controller.OrderInfoResponse;
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoResponseConverter;
import com.rmamedov.deasy.orderservice.service.OrderService;
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

    private final OrderCreateRequestToOrderConverter requestToOrderConverter;

    private final OrderToOrderInfoResponseConverter responseConverter;

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Mono<OrderInfoResponse> create(@RequestBody @Validated final OrderCreateRequest createRequest) {
        return orderService
                .create(requestToOrderConverter.convert(createRequest))
                .map(responseConverter::convert);
    }

    @GetMapping(path = "/id/{id}")
    public Mono<OrderInfoResponse> all(@PathVariable final String id) {
        return orderService
                .findById(id)
                .map(responseConverter::convert);
    }

    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderInfoResponse> all() {
        return orderService
                .findAll()
                .map(responseConverter::convert);
    }

}
