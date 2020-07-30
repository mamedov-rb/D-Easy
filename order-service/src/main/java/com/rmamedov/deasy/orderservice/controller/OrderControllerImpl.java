package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.controller.OrderInfo;
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverter;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import com.rmamedov.deasy.orderservice.receiver.CheckOrderKafkaReceiver;
import com.rmamedov.deasy.orderservice.service.CheckOrderService;
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

import java.time.Duration;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    private final CheckOrderKafkaReceiver checkOrderKafkaReceiver;

    private final CheckOrderService checkOrderService;

    private final OrderToOrderInfoConverter orderToOrderInfoConverter;

    private final OrderCreateRequestToOrderConverter requestToOrderConverter;

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Mono<OrderCreateResponse> create(@RequestBody @Validated final Mono<OrderCreateRequest> createRequest) {
        return createRequest
                .map(requestToOrderConverter::convert)
                .flatMap(checkOrderService::createAndSend);
    }

    @Override
    @GetMapping(path = "/statuses", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderCheckInfo> statuses() {
        return checkOrderKafkaReceiver.listenCheckedOrders()
                .timeout(Duration.ofSeconds(20))
                .delayElements(Duration.ofSeconds(2));
    }

    @Override
    @GetMapping(path = "/find/{id}/{checkStatus}/{paymentStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<OrderInfo> findByIdAndCheckStatus(@PathVariable("id") final String id,
                                                  @PathVariable("checkStatus") final String checkStatus,
                                                  @PathVariable("paymentStatus") final String paymentStatus) {

        return orderService
                .findByCriteria(id, checkStatus, paymentStatus)
                .map(orderToOrderInfoConverter::convert);
    }

    @Override
    @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderInfo> findAll() {
        return orderService
                .findAll()
                .map(orderToOrderInfoConverter::convert);
    }

}
