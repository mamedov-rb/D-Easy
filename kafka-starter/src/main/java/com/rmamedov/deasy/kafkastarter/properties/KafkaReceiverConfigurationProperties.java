package com.rmamedov.deasy.kafkastarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "kafka.receiver")
public class KafkaReceiverConfigurationProperties {

    @NotBlank
    private String bootstrapServers;

    @NotBlank
    private String groupId;

}
