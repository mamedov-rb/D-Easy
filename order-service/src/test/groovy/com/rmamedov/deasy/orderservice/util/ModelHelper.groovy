package com.rmamedov.deasy.orderservice.util

import com.rmamedov.deasy.application.model.CheckStatus
import com.rmamedov.deasy.application.model.OrderPosition
import com.rmamedov.deasy.orderservice.model.controller.OrderCheckInfo
import com.rmamedov.deasy.orderservice.model.controller.OrderCreateRequest

import java.time.LocalDateTime

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class ModelHelper {

    public static final String ORDER_DESCRIPTION = randomAlphabetic(10)
    public static final String CONSUMER_ADDRESS = randomAlphabetic(10)
    public static final String RESTAURANT_ADDRESS = randomAlphabetic(10)
    public static final String POSITION_1_NAME = "Abc"
    public static final String POSITION_2_NAME = "Def"
    public static final String POSITION_3_NAME = "Ghi"
    public static final String ORDER_NAME = """$POSITION_1_NAME, $POSITION_2_NAME, $POSITION_3_NAME"""

    static orderCreateRequest() {
        def createRequest = new OrderCreateRequest()
        createRequest.description = ORDER_DESCRIPTION
        createRequest.consumerAddress = CONSUMER_ADDRESS
        createRequest.restaurantAddress = RESTAURANT_ADDRESS
        createRequest.setOrderPositions(Set.of(position1(), position2(), position3()))
        return createRequest
    }

    static OrderPosition position1() {
        def position1 = new OrderPosition()
        position1.name = POSITION_1_NAME
        position1.quantity = 3
        position1.price = 400.00
        position1.discount = 10
        position1.weight = 0.5
        position1.width = 40.00
        position1.height = 5.00
        position1.length = 40.00
        position1.additionalInfo = randomAlphabetic(10)
        position1
    }

    static OrderPosition position2() {
        def position2 = new OrderPosition()
        position2.name = POSITION_2_NAME
        position2.quantity = 2
        position2.price = 700.00
        position2.discount = 13
        position2.weight = 1.5
        position2.width = 35.00
        position2.height = 60.00
        position2.length = 51.00
        position2.additionalInfo = randomAlphabetic(10)
        position2
    }

    static OrderPosition position3() {
        def position3 = new OrderPosition()
        position3.name = POSITION_3_NAME
        position3.quantity = 4
        position3.price = 100.00
        position3.discount = 9
        position3.weight = 0.6
        position3.width = 15.00
        position3.height = 55.00
        position3.length = 11.00
        position3.additionalInfo = randomAlphabetic(10)
        position3
    }

    static OrderCheckInfo orderCheckInfo(final String orderId, final CheckStatus checkStatus) {
        def orderCheckInfo = new OrderCheckInfo()
        orderCheckInfo.orderId = orderId
        orderCheckInfo.checkStatuses = Set.of(checkStatus)
        orderCheckInfo.checkDetails = Map.of(checkStatus.name(), checkStatus.name())
        orderCheckInfo.created = LocalDateTime.now()
        orderCheckInfo.updated = LocalDateTime.now()
        return orderCheckInfo
    }

}
