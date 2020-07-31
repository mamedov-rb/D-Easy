package com.rmamedov.deasy.restaurantservice.controller;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.service.MenuPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/menu-position")
@RequiredArgsConstructor
public class MenuPositionControllerImpl implements MenuPositionController {

    private final MenuPositionService menuPositionService;

    @Override
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<MenuPositionDTO>> findById(@PathVariable final String id) {
        final Mono<MenuPositionDTO> dtoMono = menuPositionService.findById(id);
        return ResponseEntity.ok(dtoMono);
    }

    @Override
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<MenuPositionDTO>> findAll() {
        final Flux<MenuPositionDTO> dtoFlux = menuPositionService.findAll();
        return ResponseEntity.ok(dtoFlux);
    }

    @Override
    @PostMapping(
            value = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Mono<MenuPositionDTO>> save(@RequestBody @Validated final MenuPosition menuPosition) {
        final Mono<MenuPositionDTO> dtoMono = menuPositionService.save(menuPosition);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoMono);
    }

}
