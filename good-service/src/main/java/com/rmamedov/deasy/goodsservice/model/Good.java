package com.rmamedov.deasy.goodsservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "goods")
@EqualsAndHashCode(of = {"id", "name"})
public class Good implements Persistable<String> {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal discount;

    @NotNull
    @Positive
    private Double weight;

    @NotNull
    @Positive
    private Double width;

    @NotNull
    @Positive
    private Double length;

    @NotNull
    @Positive
    private Double height;

    private String additionalInfo;

    private Set<String> images = new HashSet<>();

    @Transient
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew;
    }

    public Good setAsNew() {
        this.isNew = true;
        return this;
    }

}
