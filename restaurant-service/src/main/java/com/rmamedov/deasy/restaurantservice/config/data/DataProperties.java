package com.rmamedov.deasy.restaurantservice.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "data")
public class DataProperties {

    @NotBlank
    private String directory;

}
