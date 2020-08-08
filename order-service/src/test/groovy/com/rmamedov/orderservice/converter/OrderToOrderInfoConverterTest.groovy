package com.rmamedov.orderservice.converter

import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverterImpl
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverter
import com.rmamedov.deasy.orderservice.converter.OrderToOrderInfoConverterImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.orderservice.util.ModelHelper.orderCreateRequest

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
        orderInfo.organisationAddress == order.organisationAddress
        orderInfo.orderPositions.sort() == order.orderPositions.sort()
        orderInfo.paymentStatus == order.paymentStatus
        orderInfo.transactionId == null
        orderInfo.created != null
    }

}
