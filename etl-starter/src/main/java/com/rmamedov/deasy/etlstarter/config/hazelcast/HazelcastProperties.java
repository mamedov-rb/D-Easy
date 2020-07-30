package com.rmamedov.deasy.etlstarter.config.hazelcast;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Validated
public class HazelcastProperties {

    @NotBlank
    private String etlCacheName;

    @NotBlank
    private String mapName;

    @NotNull
    @Positive
    private Integer timeToLive;

    @NotNull
    @Positive
    private Integer maxIdleSeconds;

}
