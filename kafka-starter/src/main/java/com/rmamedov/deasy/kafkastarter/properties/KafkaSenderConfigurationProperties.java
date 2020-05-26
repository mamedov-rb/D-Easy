package com.rmamedov.deasy.kafkastarter.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Component
@ConditionalOnProperty(prefix = "kafka.sender", value = "bootstrap-servers")
@ConfigurationProperties(prefix = "kafka.sender")
public class KafkaSenderConfigurationProperties {

    @NotBlank
    private String bootstrapServers;

}
