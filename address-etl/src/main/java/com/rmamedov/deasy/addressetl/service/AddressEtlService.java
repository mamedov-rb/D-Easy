package com.rmamedov.deasy.addressetl.service;

import com.rmamedov.deasy.addressetl.converter.OrderCheckDetailsToOrderMessageConverter;
import com.rmamedov.deasy.addressetl.converter.OrderMessageToOrderCheckDetailConverter;
import com.rmamedov.deasy.addressetl.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.addressetl.repository.OrderDetailsRepository;
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
public class AddressEtlService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderMessageToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderMessageConverter toOrderMessageConverter;

    @Transactional
    public Mono<OrderMessage> check(final Mono<OrderMessage> OrderMessage) {
        return OrderMessage
                .map(this::check)
                .flatMap(checkedOrderMessage -> {
                    final OrderAddressCheckDetails checkDetails = detailConverter.convert(checkedOrderMessage);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved address details after check: '{}'", savedDetails))
                            .map(toOrderMessageConverter::convert);
                });
    }

    private OrderMessage check(final OrderMessage OrderMessage) {
        final String consumerAddress = OrderMessage.getConsumerAddress();
        final String restaurantAddress = OrderMessage.getRestaurantAddress();
        final String successDescription = "Address is reachable, it might took 20min to deliver order.";
        final String failedDescription = "Address is reachable, it might took 20min to deliver order.";
        if (isReachable()) {
            updateCheckStatus(OrderMessage, successDescription);
        } else {
            updateCheckStatus(OrderMessage, failedDescription);
        }
        return OrderMessage;
    }

    private void updateCheckStatus(final OrderMessage OrderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ADDRESSES_CHECKED;
        OrderMessage.getCheckStatuses().add(checkStatus);
        OrderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
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
