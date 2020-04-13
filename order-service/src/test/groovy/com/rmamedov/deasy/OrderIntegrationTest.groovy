//package com.rmamedov.deasy
//
//import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender
//import com.rmamedov.deasy.model.controller.OrderCreateRequest
//import com.rmamedov.deasy.orderservice.OrderServiceApplication
//import com.rmamedov.deasy.orderservice.config.MongoRepositoryConfig
//import com.rmamedov.deasy.orderservice.config.topic.KafkaTopicCreateConfig
//import com.rmamedov.deasy.orderservice.repository.OrderRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.test.web.reactive.server.WebTestClient
//import spock.lang.Specification
//
//import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
//import static org.mockito.ArgumentMatchers.any
//import static org.mockito.Mockito.times
//import static org.mockito.Mockito.verify
//
//@SpringBootTest(
//        classes = [OrderServiceApplication.class],
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
//@AutoConfigureWebTestClient
//class OrderIntegrationTest extends Specification {
//
//    private static final String ORDER_URL = "/api/order"
//
//    @Autowired
//    private WebTestClient webTestClient
//
//    @MockBean
//    private ApplicationKafkaSender applicationKafkaSender
//
//    @MockBean
//    private KafkaTopicCreateConfig kafkaTopicCreateConfig
//
////    @MockBean
////    private MongoRepositoryConfig mongoRepositoryConfig
//
//    @MockBean
//    private OrderRepository orderRepository
//
//    def "When create new order then success"() {
//        when:
//        def exchange = performPost(ORDER_URL + "/create", buildCreateRequest())
//
//        then:
//        exchange.expectStatus().isOk()
//
////        verify(orderClient, times(1)).getOrderDetails(any(String.class))
//    }
//
//    private WebTestClient.ResponseSpec performPost(final String url, final Object body) {
//        webTestClient
//                .post()
//                .uri(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(body)
//                .exchange()
//    }
//
//    OrderCreateRequest buildCreateRequest() {
//        def request = new OrderCreateRequest()
//        request.name = randomAlphabetic(5)
//        request.description = randomAlphabetic(5)
//        return request
//    }
//
//}
