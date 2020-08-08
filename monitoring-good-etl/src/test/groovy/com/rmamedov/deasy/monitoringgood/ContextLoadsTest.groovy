package com.rmamedov.deasy.monitoringgood

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("TEST")
@SpringBootTest(classes = MonitoringGoodEtl.class)
class ContextLoadsTest extends Specification {

    @Autowired
    private ApplicationContext context

    def "Application context starts"() {
        expect:
        context
    }

}
