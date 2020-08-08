package com.rmamedov.deasy.etlstarter.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.nio.serialization.ByteArraySerializer;
import com.rmamedov.deasy.application.model.kafka.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CheckResultSerializer implements ByteArraySerializer<OrderMessage> {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] write(final OrderMessage object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public OrderMessage read(final byte[] buffer) throws IOException {
        return objectMapper.readValue(buffer, OrderMessage.class);
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
