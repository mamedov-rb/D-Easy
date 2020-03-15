package com.rmamedov.deasy.kafkastarter.topic;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Validated
public class TopicConfigurationProperties {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer partitions;

    @NotNull
    @Positive
    private Integer replicas;

}
