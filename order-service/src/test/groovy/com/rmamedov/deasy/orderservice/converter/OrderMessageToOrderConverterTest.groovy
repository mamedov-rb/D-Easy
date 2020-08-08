package com.rmamedov.deasy.orderservice.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.deasy.orderservice.util.ModelHelper.orderCreateRequest

@SpringBootTest(classes = [
        OrderMessageToOrderConverterImpl.class,
        OrderToOrderMessageConverterImpl.class,
        OrderCreateRequestToOrderConverterImpl.class
])
class OrderMessageToOrderConverterTest extends Specification {

    @Autowired
    private OrderMessageToOrderConverter orderMessageToOrderConverter

    @Autowired
    private OrderToOrderMessageConverter orderToOrderMessageConverter

    @Autowired
    private OrderCreateRequestToOrderConverter createRequestToOrderConverter

    def "Convert Order to OrderMessage"() {
        given:
        def order = createRequestToOrderConverter.convert(orderCreateRequest())
        def orderMessage = orderToOrderMessageConverter.convert(order)

        when:
        def converted = orderMessageToOrderConverter.convert(orderMessage)

        then:
        converted.id == orderMessage.id
        converted.name == orderMessage.name
        converted.description == orderMessage.description
        converted.totalPrice == orderMessage.totalPrice
        converted.totalPriceAfterDiscount == orderMessage.totalPriceAfterDiscount
        converted.totalWeight == orderMessage.totalWeight
        converted.totalVolume == orderMessage.totalVolume
        converted.consumerAddress == orderMessage.consumerAddress
        converted.restaurantAddress == orderMessage.restaurantAddress
        converted.orderPositions.sort() == orderMessage.orderPositions.sort()
        converted.paymentStatus == orderMessage.paymentStatus
        converted.transactionId == null
        converted.created != null
    }

}
