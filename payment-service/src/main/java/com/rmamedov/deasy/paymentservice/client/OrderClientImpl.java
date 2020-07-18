package com.rmamedov.deasy.paymentservice.client;

import com.rmamedov.deasy.model.kafka.OrderMessage;
import com.rmamedov.deasy.paymentservice.config.ClientConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

    private final WebClient webClient;

    private final ClientConfigurationProperties properties;

    /**
     * Returns OrderMessage or throws WebClientResponseException if smth goes wrong.
     */
    @Override
    public Mono<OrderMessage> findByCriteria(final String id,
                                             final String checkStatus,
                                             final String payStatus) {

        return webClient.get()
                .uri(properties.getUri(), id, checkStatus, payStatus)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OrderMessage.class)
                .log();
    }

}
