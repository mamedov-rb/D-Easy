package com.rmamedov.orderservice.converter

import com.rmamedov.deasy.model.kafka.PaymentStatus
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverter
import com.rmamedov.deasy.orderservice.converter.OrderCreateRequestToOrderConverterImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.orderservice.util.ModelHelper.CONSUMER_ADDRESS
import static com.rmamedov.orderservice.util.ModelHelper.ORDER_DESCRIPTION
import static com.rmamedov.orderservice.util.ModelHelper.ORDER_NAME
import static com.rmamedov.orderservice.util.ModelHelper.RESTAURANT_ADDRESS
import static com.rmamedov.orderservice.util.ModelHelper.orderCreateRequest
import static com.rmamedov.orderservice.util.ModelHelper.position1
import static com.rmamedov.orderservice.util.ModelHelper.position2
import static com.rmamedov.orderservice.util.ModelHelper.position3

@SpringBootTest(classes = OrderCreateRequestToOrderConverterImpl.class)
class OrderCreateRequestToOrderConverterTest extends Specification {

    @Autowired
    private OrderCreateRequestToOrderConverter converter

    def "Convert OrderCreateRequest to Order"() {
        when:
        def order = converter.convert(orderCreateRequest())

        then:
        order.id != null
        order.name == ORDER_NAME
        order.description == ORDER_DESCRIPTION
        order.totalPrice == 3000.00
        order.totalPriceAfterDiscount == 2662.00
        order.totalWeight == 6.9
        order.totalVolume == 274500.0
        order.consumerAddress == CONSUMER_ADDRESS
        order.restaurantAddress == RESTAURANT_ADDRESS
        order.orderPositions.sort() == [position1(), position2(), position3()].sort()
        order.paymentStatus == PaymentStatus.NEW
        order.transactionId == null
        order.created != null
    }

}
