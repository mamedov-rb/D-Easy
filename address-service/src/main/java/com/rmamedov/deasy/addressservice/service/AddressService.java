package com.rmamedov.deasy.addressservice.service;

import com.rmamedov.deasy.addressservice.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.addressservice.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.addressservice.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.addressservice.repository.OrderDetailsRepository;
import com.rmamedov.deasy.model.kafka.Address;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toMessageConverter;

    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessage) {
        return orderMessage
                .map(this::check)
                .flatMap(checkedMessage -> {
                    final OrderAddressCheckDetails checkDetails = detailConverter.convert(checkedMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved address details after check: '{}'", savedDetails))
                            .map(toMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage orderMessage) {
        final Address consumerAddress = orderMessage.getConsumerAddress();
        final Address restorauntAddress = orderMessage.getRestorauntAddress();
        final String successDescription = "Address is reachable, it might took 20min to deliver order.";
        final String failedDescription = "Address is reachable, it might took 20min to deliver order.";
        if (isReachable()) {
            updateCheckStatus(orderMessage, successDescription);
        } else {
            updateCheckStatus(orderMessage, failedDescription);
        }
        return orderMessage;
    }

    private void updateCheckStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ADDRESSES_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Addresses checked with result: '{}'.", checkDetails);
    }

    private boolean isReachable() {
        return true; // TODO 2020-03-22 rustammamedov: Do real check using google library;
    }

//    public static GeoPoint getGeoPointFromAddress(String locationAddress) {
//        GeoPoint locationPoint = null;
//        String locationAddres = locationAddress.replaceAll(" ", "%20");
//        String str = "http://maps.googleapis.com/maps/api/geocode/json?address="
//                + locationAddres + "&sensor=true";
//
//        String ss = readWebService(str);
//        JSONObject json;
//        try {
//            String lat, lon;
//            json = new JSONObject(ss);
//            JSONObject geoMetryObject = new JSONObject();
//            JSONObject locations = new JSONObject();
//            JSONArray jarr = json.getJSONArray("results");
//            int i;
//            for (i = 0; i < jarr.length(); i++) {
//                json = jarr.getJSONObject(i);
//                geoMetryObject = json.getJSONObject("geometry");
//                locations = geoMetryObject.getJSONObject("location");
//                lat = locations.getString("lat");
//                lon = locations.getString("lng");
//
//                locationPoint = Utils.getGeoPoint(Double.parseDouble(lat),
//                        Double.parseDouble(lon));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return locationPoint;
//    }

}
