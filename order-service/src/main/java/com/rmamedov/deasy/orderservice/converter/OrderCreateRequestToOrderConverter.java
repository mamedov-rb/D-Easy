package com.rmamedov.deasy.orderservice.converter;

import com.rmamedov.deasy.model.controller.OrderCreateRequest;
import com.rmamedov.deasy.model.kafka.OrderPosition;
import com.rmamedov.deasy.model.kafka.PaymentStatus;
import com.rmamedov.deasy.orderservice.model.repository.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderCreateRequestToOrderConverter {

    BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    Order convert(OrderCreateRequest createRequest);

    @AfterMapping
    default void convertNameToUpperCase(@MappingTarget Order order, OrderCreateRequest orderCreateRequest) {
        order.setPaymentStatus(PaymentStatus.NEW);
        defineOrderName(order, orderCreateRequest);
        defineTotalPrice(order, orderCreateRequest);
        defineTotalPriceAfterDiscount(order, orderCreateRequest);
        defineTotalWeight(order, orderCreateRequest);
        defineTotalVolume(order, orderCreateRequest);
    }

    private void defineOrderName(final Order order, final OrderCreateRequest orderCreateRequest) {
        final String positionsNames = orderCreateRequest
                .getOrderPositions()
                .stream()
                .map(OrderPosition::getName)
                .collect(Collectors.joining(", "));
        order.setName(positionsNames);
    }

    private void defineTotalPrice(final Order order, final OrderCreateRequest orderCreateRequest) {
        final BigDecimal totalPrice = orderCreateRequest
                .getOrderPositions()
                .stream()
                .map(p -> {
                    final Integer quantity = p.getQuantity();
                    final BigDecimal price = p.getPrice();
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
    }

    private void defineTotalPriceAfterDiscount(final Order order, final OrderCreateRequest orderCreateRequest) {
        final BigDecimal totalPriceAfterDiscount = orderCreateRequest
                .getOrderPositions()
                .stream()
                .map(p -> {
                    final Integer quantity = p.getQuantity();
                    final BigDecimal price = p.getPrice();
                    final BigDecimal priceByCount = price.multiply(BigDecimal.valueOf(quantity));
                    final BigDecimal discount = p.getDiscount();
                    final BigDecimal discountPrice = priceByCount.multiply(discount).divide(ONE_HUNDRED);
                    return priceByCount.subtract(discountPrice);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPriceAfterDiscount(totalPriceAfterDiscount);
    }

    private void defineTotalWeight(final Order order, final OrderCreateRequest orderCreateRequest) {
        final Double totalWeight = orderCreateRequest
                .getOrderPositions()
                .stream()
                .mapToDouble(p -> {
                    final Integer quantity = p.getQuantity();
                    final Double weight = p.getWeight();
                    return weight * quantity;
                })
                .sum();
        order.setTotalWeight(totalWeight);
    }

    private void defineTotalVolume(final Order order, final OrderCreateRequest orderCreateRequest) {
        final Double totalVolume = orderCreateRequest
                .getOrderPositions()
                .stream()
                .mapToDouble(p -> {
                    final Integer quantity = p.getQuantity();
                    final var width = p.getWidth();
                    final var height = p.getHeight();
                    final var length = p.getLength();
                    return width * height * length * quantity;
                })
                .sum();
        order.setTotalVolume(totalVolume);  //TODO 2020-07-04 rustammamedov: return meters.
    }

}
