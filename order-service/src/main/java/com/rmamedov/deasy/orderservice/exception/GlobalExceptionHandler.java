package com.rmamedov.deasy.orderservice.exception;

import com.rmamedov.deasy.application.model.exceptions.OrderNotFoundException;
import com.rmamedov.deasy.application.model.exceptions.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ResponseModel>> handleBeenValidationException(final WebExchangeBindException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return buildResponse(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public Mono<ResponseEntity<ResponseModel>> handleOrderNotFoundException(final OrderNotFoundException ex) {
        log.error("OrderNotFoundException: ", ex);
        return buildResponse(ex.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<ResponseEntity<ResponseModel>> handleWebClientResponseException(final WebClientResponseException ex) {
        log.error("WebClientResponseException: Status: '{}', Body: '{}'", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
        return buildResponse(ex.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ResponseModel>> handleOthersException(final Exception ex) {
        log.error("Exception has occurred: ", ex);
        return buildResponse(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Mono<ResponseEntity<ResponseModel>> buildResponse(final String body, final HttpStatus status) {
        return Mono.just(ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseModel.builder()
                        .message(body)
                        .timestamp(LocalDateTime.now()).build()
                )
        );
    }

}
