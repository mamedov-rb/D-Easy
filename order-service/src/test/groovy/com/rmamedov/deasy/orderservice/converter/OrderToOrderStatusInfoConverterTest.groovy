package com.rmamedov.deasy.orderservice.converter

import com.rmamedov.deasy.application.model.CheckStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.rmamedov.deasy.orderservice.util.ModelHelper.orderCreateRequest

@SpringBootTest(classes = [
        OrderCreateRequestToOrderConverterImpl.class,
        OrderToOrderStatusInfoConverterImpl.class
])
class OrderToOrderStatusInfoConverterTest extends Specification {

    private static final Set<CheckStatus> CHECK_STATUSES = Set.of(CheckStatus.ADDRESSES_CHECKED, CheckStatus.COURIER_CHECKED)
    private static final Map<String, String> CHECK_DETAILS = Map.of("1", "1", "2", "2")

    @Autowired
    private OrderCreateRequestToOrderConverter createRequestToOrderConverter

    @Autowired
    private OrderToOrderStatusInfoConverter toOrderStatusInfoConverter

    def "Convert Order to OrderStatusInfo"() {
        given:
        def order = createRequestToOrderConverter.convert(orderCreateRequest())
        order.checkStatuses = CHECK_STATUSES
        order.checkDetails = CHECK_DETAILS

        when:
        def statusInfo = toOrderStatusInfoConverter.convert(order)

        then:
        statusInfo.orderId == order.id
        statusInfo.checkStatuses.sort() == CHECK_STATUSES.sort()
        statusInfo.checkDetails.sort() == CHECK_DETAILS.sort()
        statusInfo.created != null
    }

}
