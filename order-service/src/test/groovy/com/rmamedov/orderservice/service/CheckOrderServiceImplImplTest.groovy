package com.rmamedov.orderservice.service

import com.rmamedov.deasy.kafkastarter.properties.KafkaSenderProperties
import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender
import com.rmamedov.deasy.model.kafka.CheckStatus
import com.rmamedov.deasy.orderservice.config.kafka.KafkaSenderConfig
import com.rmamedov.deasy.orderservice.config.kafka.TopicPropertiesConfig
import com.rmamedov.deasy.orderservice.config.mongo.MongoConfigurationProperties
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverterImpl
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverter
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverterImpl
import com.rmamedov.deasy.orderservice.converter.OrderToOrderStatusInfoConverter
import com.rmamedov.deasy.orderservice.converter.OrderToOrderStatusInfoConverterImpl
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateResponse
import com.rmamedov.deasy.orderservice.model.repository.Order
import com.rmamedov.deasy.orderservice.service.CheckOrderService
import com.rmamedov.deasy.orderservice.service.CheckOrderServiceImpl
import com.rmamedov.deasy.orderservice.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import static com.rmamedov.orderservice.util.ModelHelper.orderCheckInfo
import static com.rmamedov.orderservice.util.ModelHelper.orderCreateRequest
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

@ContextConfiguration(classes = [
        CheckOrderServiceImpl.class,
        KafkaSenderConfig.class,
        KafkaSenderProperties.class,
        TopicPropertiesConfig.class,
        OrderToOrderMessageConverterImpl.class,
        OrderToOrderStatusInfoConverterImpl.class,
        OrderCreateRequestToOrderConverterImpl.class
])
class CheckOrderServiceImplImplTest extends Specification {

    @MockBean
    private OrderService orderService

    @Autowired
    private CheckOrderService checkOrderService

    @MockBean
    private MongoConfigurationProperties mongoProperties

    @MockBean
    @Qualifier("newOrdersSender")
    private ApplicationKafkaSender applicationKafkaSender

    @MockBean
    private KafkaSenderProperties kafkaSenderConfigurationProperties

    @Autowired
    private OrderToOrderMessageConverter orderToOrderMessageConverter

    @Autowired
    private OrderCreateRequestToOrderConverter requestToOrderConverter

    @Autowired
    private OrderToOrderStatusInfoConverter orderToOrderStatusInfoConverter

    def "When save new order then success"() {
        given:
        def order = requestToOrderConverter.convert(orderCreateRequest())
        def createResponse = new OrderCreateResponse(order.getId())
        when(orderService.save(any(Order.class))).thenReturn(Mono.just(order))

        when:
        def createAndSend = checkOrderService.createAndSend(order)

        then:
        StepVerifier
                .create(createAndSend)
                .expectNext(createResponse)
                .expectComplete()
                .verify()
    }

    def "When update order after AddressETL check then success"() {
        given:
        def oldOrder = requestToOrderConverter.convert(orderCreateRequest())
        def incomingOrder = requestToOrderConverter.convert(orderCreateRequest())
        def checkStatus = CheckStatus.ADDRESSES_CHECKED
        def checkInfo = orderCheckInfo(oldOrder.id, CheckStatus.ADDRESSES_CHECKED)
        checkInfo.created = oldOrder.created
        checkInfo.updated = oldOrder.updated
        incomingOrder.checkStatuses = Set.of(checkStatus)
        incomingOrder.checkDetails = Map.of(checkStatus.name(), checkStatus.name())
        when(orderService.findById(any(String.class))).thenReturn(Mono.just(oldOrder))
        when(orderService.save(any(Order.class))).thenReturn(Mono.just(oldOrder))

        when:
        def afterEtlCheck = checkOrderService.updateOrderAfterEtlCheck(incomingOrder)

        then:
        StepVerifier
                .create(afterEtlCheck)
                .expectNext(checkInfo)
                .expectComplete()
                .verify()
    }

}
