package com.rmamedov.deasy.kafkastarter.properties;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class KafkaSenderProperties {

    @NotBlank
    private String bootstrapServers;

}
