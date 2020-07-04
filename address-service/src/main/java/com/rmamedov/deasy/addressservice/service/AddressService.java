package com.rmamedov.deasy.addressservice.service;

import com.rmamedov.deasy.addressservice.converter.OrderCheckDetailsToOrderDtoConverter;
import com.rmamedov.deasy.addressservice.converter.OrderDtoToOrderCheckDetailConverter;
import com.rmamedov.deasy.addressservice.model.OrderAddressCheckDetails;
import com.rmamedov.deasy.addressservice.repository.OrderDetailsRepository;
import com.rmamedov.deasy.model.kafka.Address;
import com.rmamedov.deasy.model.kafka.CheckStatus;
import com.rmamedov.deasy.model.kafka.OrderDto;
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

    private final OrderDtoToOrderCheckDetailConverter detailConverter;

    private final OrderCheckDetailsToOrderDtoConverter toDtoConverter;

    @Transactional
    public Mono<OrderDto> check(final Mono<OrderDto> orderDto) {
        return orderDto
                .map(this::check)
                .flatMap(checkedDto -> {
                    final OrderAddressCheckDetails checkDetails = detailConverter.convert(checkedDto);
                    return orderDetailsRepository.save(checkDetails)
                            .doOnNext(savedDetails -> log.info("Saved address details after check: '{}'", savedDetails))
                            .map(toDtoConverter::convert);
                });
    }

    private OrderDto check(final OrderDto orderDto) {
        final Address consumerAddress = orderDto.getConsumerAddress();
        final Address restaurantAddress = orderDto.getRestaurantAddress();
        final String successDescription = "Address is reachable, it might took 20min to deliver order.";
        final String failedDescription = "Address is reachable, it might took 20min to deliver order.";
        if (isReachable()) {
            updateCheckStatus(orderDto, successDescription);
        } else {
            updateCheckStatus(orderDto, failedDescription);
        }
        return orderDto;
    }

    private void updateCheckStatus(final OrderDto orderDto, final String checkDetails) {
        final CheckStatus checkStatus = CheckStatus.ADDRESSES_CHECKED;
        orderDto.getCheckStatuses().add(checkStatus);
        orderDto.getCheckDetails().put(checkStatus.name(), checkDetails);
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
