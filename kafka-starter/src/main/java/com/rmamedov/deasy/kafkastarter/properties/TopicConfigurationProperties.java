package com.rmamedov.deasy.kafkastarter.properties;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class TopicConfigurationProperties {

    @NotBlank
    private String name;

    private Integer partitions;

    private Integer replicas;

}
