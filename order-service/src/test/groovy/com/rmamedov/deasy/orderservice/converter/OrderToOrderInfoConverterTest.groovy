package com.rmamedov.deasy.orderservice.converter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.deasy.orderservice.util.ModelHelper.orderCreateRequest

@SpringBootTest(classes = [
        OrderCreateRequestToOrderConverterImpl.class,
        OrderToOrderInfoConverterImpl.class
])
class OrderToOrderInfoConverterTest extends Specification {

    @Autowired
    private OrderCreateRequestToOrderConverter createRequestToOrderConverter

    @Autowired
    private OrderToOrderInfoConverter orderToOrderInfoConverter

    def "Convert Order to OrderInfo"() {
        given:
        def order = createRequestToOrderConverter.convert(orderCreateRequest())

        when:
        def orderInfo = orderToOrderInfoConverter.convert(order)

        then:
        orderInfo.id == order.id
        orderInfo.name == order.name
        orderInfo.description == order.description
        orderInfo.totalPrice == order.totalPrice
        orderInfo.totalPriceAfterDiscount == order.totalPriceAfterDiscount
        orderInfo.totalWeight == order.totalWeight
        orderInfo.totalVolume == order.totalVolume
        orderInfo.consumerAddress == order.consumerAddress
        orderInfo.restaurantAddress == order.restaurantAddress
        orderInfo.orderPositions.sort() == order.orderPositions.sort()
        orderInfo.paymentStatus == order.paymentStatus
        orderInfo.transactionId == null
        orderInfo.created != null
    }

}
