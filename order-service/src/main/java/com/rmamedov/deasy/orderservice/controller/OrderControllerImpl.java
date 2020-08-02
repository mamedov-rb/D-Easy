package com.rmamedov.deasy.orderservice.controller;

import com.rmamedov.deasy.kafkastarter.properties.TopicProperties;
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter;
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverter;
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse;
import com.rmamedov.deasy.orderservice.model.controller.OrderInfo;
import com.rmamedov.deasy.orderservice.receiver.CheckOrderKafkaReceiver;
import com.rmamedov.deasy.orderservice.service.CheckOrderService;
import com.rmamedov.deasy.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    private final CheckOrderKafkaReceiver checkOrderKafkaReceiver;

    private final CheckOrderService checkOrderService;

    private final OrderToOrderInfoConverter orderToOrderInfoConverter;

    private final OrderCreateRequestToOrderConverter requestToOrderConverter;

    private final List<TopicProperties> topicConfigurationList;

    private long etlCount;

    @PostConstruct
    public void init() {
        etlCount = topicConfigurationList.stream()
                .map(TopicProperties::getName)
                .filter(name -> name.startsWith("checked"))
                .count();
    }

    @Override
    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Mono<OrderCreateResponse>> create(@RequestBody @Validated final Mono<OrderCreateRequest> createRequest) {
        final Mono<OrderCreateResponse> responseMono = createRequest
                .map(requestToOrderConverter::convert)
                .flatMap(checkOrderService::createAndSend);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMono);
    }

    @Override
    @GetMapping(path = "/find/{id}/{checkStatus}/{paymentStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<OrderInfo>> findByIdAndCheckStatus(@PathVariable("id") final String id,
                                                                  @PathVariable("checkStatus") final String checkStatus,
                                                                  @PathVariable("paymentStatus") final String paymentStatus) {

        final Mono<OrderInfo> orderInfoMono = orderService
                .findByCriteria(id, checkStatus, paymentStatus)
                .map(orderToOrderInfoConverter::convert);
        return ResponseEntity.ok(orderInfoMono);
    }

    @Override
    @GetMapping(path = "/statuses", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<OrderCheckInfo>> statuses() {
        log.info("{} ETL are checked Order.", etlCount);
        final Flux<OrderCheckInfo> checkInfoFlux = checkOrderKafkaReceiver.listenCheckedOrders()
                .take(etlCount)
                .delayElements(Duration.ofSeconds(2));
        return ResponseEntity.ok(checkInfoFlux);
    }

    @Override
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<OrderInfo>> findAll() {
        final Flux<OrderInfo> infoFlux = orderService
                .findAll()
                .map(orderToOrderInfoConverter::convert)
                .delayElements(Duration.ofSeconds(2));
        return ResponseEntity.ok(infoFlux);
    }

}
