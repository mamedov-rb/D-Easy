package com.rmamedov.deasy.paymentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "order-service-client")
public class ClientConfigurationProperties {

//    @NotBlank
//    private String host;

    @NotBlank
    private String uri;

    @NotNull
    @Positive
    private Integer connectionTimeout;

    @NotNull
    @Positive
    private Integer readTimeout;

}
