package com.rmamedov.deasy.orderservice.config.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoProperties {

    @NotNull
    @Positive
    private Integer numRetries;

    @NotNull
    @Positive
    private Integer firstBackoff;

    @NotNull
    @Positive
    private Integer maxBackoff;

}
