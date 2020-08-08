package com.rmamedov.orderservice.converter

import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverterImpl
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverter
import com.rmamedov.deasy.orderservice.converter.OrderToOrderMessageConverterImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.orderservice.util.ModelHelper.orderCreateRequest

@SpringBootTest(classes = [
        OrderToOrderMessageConverterImpl.class,
        OrderCreateRequestToOrderConverterImpl.class
])
class OrderToOrderMessageConverterTest extends Specification {

    @Autowired
    private OrderToOrderMessageConverter orderToOrderMessageConverter

    @Autowired
    private OrderCreateRequestToOrderConverter createRequestToOrderConverter

    def "Convert Order to OrderMessage"() {
        given:
        def order = createRequestToOrderConverter.convert(orderCreateRequest())

        when:
        def orderMessage = orderToOrderMessageConverter.convert(order)

        then:
        orderMessage.id == order.id
        orderMessage.name == order.name
        orderMessage.description == order.description
        orderMessage.totalPrice == order.totalPrice
        orderMessage.totalPriceAfterDiscount == order.totalPriceAfterDiscount
        orderMessage.totalWeight == order.totalWeight
        orderMessage.totalVolume == order.totalVolume
        orderMessage.consumerAddress == order.consumerAddress
        orderMessage.organisationAddress == order.organisationAddress
        orderMessage.orderPositions.sort() == order.orderPositions.sort()
        orderMessage.paymentStatus == order.paymentStatus
        orderMessage.transactionId == null
        orderMessage.created != null
    }

}
