package com.rmamedov.deasy.restaurantservice.service;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.model.dto.UploadDetailsDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuPositionService {

    Mono<MenuPositionDTO> save(MenuPosition position);

    Mono<MenuPositionDTO> findById(String id);

    Flux<MenuPositionDTO> findAll();

    Mono<UploadDetailsDTO> uploadAndAdd(FilePart filePartFlux, String positionId);

    Resource findFile(final String fileName);

}
