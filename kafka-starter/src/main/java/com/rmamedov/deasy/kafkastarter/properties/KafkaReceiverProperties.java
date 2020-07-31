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
@ConditionalOnProperty(prefix = "kafka.receiver", value = "bootstrap-servers")
@ConfigurationProperties(prefix = "kafka.receiver")
public class KafkaReceiverProperties {

    @NotBlank
    private String bootstrapServers;

    @NotBlank
    private String groupId;

}
