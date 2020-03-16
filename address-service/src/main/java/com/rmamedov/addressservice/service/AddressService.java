//package com.rmamedov.addressservice.service;
//
//import com.rmamedov.deasy.kafkastarter.sender.ApplicationKafkaSender;
//import com.rmamedov.deasy.model.converter.OrderToOrderMessageConverter;
//import com.rmamedov.deasy.model.repository.Order;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class AddressService {
//
//    private final OrderToOrderMessageConverter orderToOrderMessageConverter;
//
//    private final ApplicationKafkaSender applicationKafkaSender;
//
//    public AddressService(OrderToOrderMessageConverter orderToOrderMessageConverter,
//                          @Qualifier("checkedAddressSender") ApplicationKafkaSender applicationKafkaSender) {
//
//        this.orderToOrderMessageConverter = orderToOrderMessageConverter;
//        this.applicationKafkaSender = applicationKafkaSender;
//    }
//
//    public void checkAddress(final Order order) {
//
//
//
//    }
//
//}
