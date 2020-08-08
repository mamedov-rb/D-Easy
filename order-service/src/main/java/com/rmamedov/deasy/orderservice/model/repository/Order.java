package com.rmamedov.deasy.orderservice.model.repository;

import com.rmamedov.deasy.application.model.CheckStatus;
import com.rmamedov.deasy.application.model.OrderPosition;
import com.rmamedov.deasy.application.model.PaymentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "orders")
@EqualsAndHashCode(of = {"id", "name", "description"})
public class Order implements Persistable<String> {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private String description;

    private BigDecimal totalPrice;

    private BigDecimal totalPriceAfterDiscount;

    private Double totalWeight;

    private Double totalVolume;

    private String consumerAddress;

    private String restaurantAddress;

    private Set<OrderPosition> orderPositions;

    private Set<CheckStatus> checkStatuses = new HashSet<>();

    private Map<String, String> checkDetails = new HashMap<>();

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime created = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updated;

    @Transient
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew;
    }

    public Order setAsNew() {
        this.isNew = true;
        return this;
    }

}
