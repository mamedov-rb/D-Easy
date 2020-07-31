package com.rmamedov.deasy.courieretl

import com.rmamedov.deasy.restaurantservice.RestaurantApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest(classes = RestaurantApplication.class)
class ContextLoadsTest extends Specification {

    @Autowired
    private ApplicationContext context

    def "Application context starts"() {
        expect:
        context
    }

}
