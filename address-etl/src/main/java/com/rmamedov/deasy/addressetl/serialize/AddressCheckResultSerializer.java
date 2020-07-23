package com.rmamedov.deasy.addressetl.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.nio.serialization.ByteArraySerializer;
import com.rmamedov.deasy.addressetl.model.AddressCheckResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AddressCheckResultSerializer implements ByteArraySerializer<AddressCheckResult> {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] write(final AddressCheckResult object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public AddressCheckResult read(final byte[] buffer) throws IOException {
        return objectMapper.readValue(buffer, AddressCheckResult.class);
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
