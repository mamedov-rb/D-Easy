package com.rmamedov.deasy.orderservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class MvcPerformer {

    static ObjectMapper objectMapper = new ObjectMapper()

    static get(final WebTestClient webTestClient, final String url, final String... args) {
        webTestClient
                .get()
                .uri(url, args)
                .exchange()
    }

    static post(final WebTestClient webTestClient, final String url, final Object body) {
        webTestClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(body))
                .exchange()
    }

}
