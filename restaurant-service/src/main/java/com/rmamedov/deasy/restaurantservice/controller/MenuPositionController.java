package com.rmamedov.deasy.restaurantservice.controller;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.model.dto.UploadDetailsDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuPositionController {

    ResponseEntity<Mono<MenuPositionDTO>> findById(String id);

    ResponseEntity<Flux<MenuPositionDTO>> findAll();

    ResponseEntity<Mono<MenuPositionDTO>> save(MenuPosition menuPosition);

    ResponseEntity<Flux<UploadDetailsDTO>> save(final Flux<FilePart> filePartMono, final String positionId);

    ResponseEntity<Mono<? extends Resource>> findFile(String fileName);

}
