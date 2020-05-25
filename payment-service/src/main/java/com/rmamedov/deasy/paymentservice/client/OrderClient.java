package com.rmamedov.deasy.paymentservice.client;

import com.rmamedov.deasy.model.kafka.OrderDto;
import com.rmamedov.deasy.paymentservice.config.ClientConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final WebClient webClient;

    private final ClientConfigurationProperties properties;

    public Mono<OrderDto> findById(final String id) {
        return webClient.get()
                .uri(properties.getUri(), id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OrderDto.class);
    }

}
