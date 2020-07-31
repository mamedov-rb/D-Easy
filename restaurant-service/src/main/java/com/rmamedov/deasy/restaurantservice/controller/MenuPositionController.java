package com.rmamedov.deasy.restaurantservice.controller;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuPositionController {

    ResponseEntity<Mono<MenuPositionDTO>> findById(String id);

    ResponseEntity<Flux<MenuPositionDTO>> findAll();

    ResponseEntity<Mono<MenuPositionDTO>> save(MenuPosition menuPosition);

}
