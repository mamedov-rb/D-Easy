package com.rmamedov.orderservice.rest

import com.rmamedov.deasy.model.controller.OrderInfo
import com.rmamedov.deasy.model.kafka.CheckStatus
import com.rmamedov.deasy.model.kafka.PaymentStatus
import com.rmamedov.deasy.orderservice.controller.OrderController
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverterImpl
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverter
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverterImpl
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse
import com.rmamedov.deasy.orderservice.model.repository.Order
import com.rmamedov.deasy.orderservice.receiver.CheckOrderKafkaReceiver
import com.rmamedov.deasy.orderservice.service.CheckOrderService
import com.rmamedov.deasy.orderservice.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.util.stream.Stream

import static com.rmamedov.orderservice.util.ModelHelper.orderCheckInfo
import static com.rmamedov.orderservice.util.ModelHelper.orderCreateRequest
import static com.rmamedov.orderservice.util.MvcPerformer.get
import static com.rmamedov.orderservice.util.MvcPerformer.post
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

@WebFluxTest(OrderController.class)
@ContextConfiguration(classes = [
        OrderController.class,
        OrderCreateRequestToOrderConverterImpl.class,
        OrderToOrderInfoConverterImpl.class
])
class OrderIntegrationTest extends Specification {

    private static final String CREATE_ORDER_URL = "/api/order/create"
    private static final String CHECK_ORDER_STATUSES_URL = "/api/order/statuses"
    private static final String FIND_ORDER_BY_CRITERIA_URL = "/api/order/find/{id}/{checkStatus}/{paymentStatus}"
    private static final String ALL_ORDERS_URL = "/api/order/all"

    @Autowired
    private WebTestClient webTestClient

    @MockBean
    private OrderService orderService

    @MockBean
    private CheckOrderKafkaReceiver checkOrderKafkaReceiver

    @MockBean
    private CheckOrderService checkOrderService

    @Autowired
    private OrderCreateRequestToOrderConverter requestToOrderConverter

    @Autowired
    private OrderToOrderInfoConverter orderToOrderInfoConverter

    def "When create new order then success"() {
        given:
        def orderId = randomAlphabetic(10)
        when(checkOrderService.createAndSend(any(Order.class)))
                .thenReturn(Mono.just(new OrderCreateResponse(orderId)))

        when:
        def exchange = post(webTestClient, CREATE_ORDER_URL, orderCreateRequest())

        then:
        exchange
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath('$.orderId', orderId)
    }

    def "When ask all order statuses then success"() {
        given:
        final List<OrderCheckInfo> list = new ArrayList<>()
        Stream.of(CheckStatus.values())
                .filter({ c -> c != CheckStatus.FULLY_CHECKED })
                .limit(2)
                .forEach({ c -> list.add(orderCheckInfo(c)) })
        list.add(orderCheckInfo(CheckStatus.FULLY_CHECKED))
        when(checkOrderKafkaReceiver.listenCheckedOrders()).thenReturn(Flux.fromIterable(list))

        when:
        def exchange = get(webTestClient, CHECK_ORDER_STATUSES_URL)

        then:
        exchange
                .expectStatus().isOk()
                .expectBodyList(OrderCheckInfo.class)
                .isEqualTo(list)
    }

    def "When find order by criteria then success"() {
        given:
        def order = requestToOrderConverter.convert(orderCreateRequest())
        def orderInfo = orderToOrderInfoConverter.convert(order)
        when(orderService.findByCriteria(any(String.class), any(String.class), any(String.class)))
                .thenReturn(Mono.just(order))

        when:
        def exchange = get(
                webTestClient,
                FIND_ORDER_BY_CRITERIA_URL,
                order.id,
                CheckStatus.FULLY_CHECKED.name(),
                PaymentStatus.NEW.name()
        )

        then:
        exchange
                .expectStatus().isOk()
                .expectBody(OrderInfo.class)
                .isEqualTo(orderInfo)
    }

    def "When find all orders then success"() {
        given:
        def order = requestToOrderConverter.convert(orderCreateRequest())
        def orderInfo = orderToOrderInfoConverter.convert(order)
        when(orderService.findAll())
                .thenReturn(Flux.just(order))

        when:
        def exchange = get(webTestClient, ALL_ORDERS_URL)

        then:
        exchange
                .expectStatus().isOk()
                .expectBodyList(OrderInfo.class)
                .isEqualTo(List.of(orderInfo))
    }

}
