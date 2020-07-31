package com.rmamedov.deasy.restaurantservice.service;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuPositionService {

    Mono<MenuPositionDTO> save(MenuPosition position);

    Mono<MenuPositionDTO> findById(String id);

    Flux<MenuPositionDTO> findAll();

}
