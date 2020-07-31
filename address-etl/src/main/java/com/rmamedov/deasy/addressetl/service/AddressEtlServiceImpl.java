package com.rmamedov.deasy.addressetl.service;

import com.rmamedov.deasy.etlstarter.service.OrderEtlService;
import com.rmamedov.deasy.model.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressEtlServiceImpl implements OrderEtlService {

    @Override
    public Mono<OrderMessage> check(final Mono<OrderMessage> orderMessageMono) {
        final String successDescription = "Address is reachable, it might took 20min to deliver order.";
        final String failedDescription = "Address is reachable, it might took 20min to deliver order.";
        return orderMessageMono
                .map(orderMessage -> updateStatus(orderMessage, successDescription));
    }

    private OrderMessage updateStatus(final OrderMessage orderMessage, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ADDRESSES_CHECKED;
        orderMessage.getCheckStatuses().add(checkStatus);
        orderMessage.getCheckDetails().put(checkStatus.name(), checkDetails);
        log.info("Addresses checked with result: '{}'.", checkDetails);
        return orderMessage;
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
