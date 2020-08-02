package com.rmamedov.deasy.restaurantservice.controller;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.model.dto.UploadDetailsDTO;
import com.rmamedov.deasy.restaurantservice.service.MenuPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static java.time.Duration.ofMillis;

@Slf4j
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
        final Flux<MenuPositionDTO> dtoFlux = menuPositionService.findAll()
                .delayElements(ofMillis(500));
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

    @Override
    @PostMapping(
            value = "/upload-files/{positionId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE
    )
    public ResponseEntity<Flux<UploadDetailsDTO>> save(@RequestPart("files") final Flux<FilePart> filePartFlux,
                                                       @PathVariable("positionId") final String positionId) {

        final var stringFlux = filePartFlux.flatMap(filePart -> {
            Assert.isTrue(filePart.filename().length() > 0, "File not provided.");
            return menuPositionService.uploadAndAdd(filePart, positionId)
                    .retryBackoff(10, ofMillis(10), ofMillis(1000), Schedulers.elastic())
                    .doOnError(ex -> log.warn("Concurrent access to DB with retry: {}", ex.getMessage()));
        });
        return ResponseEntity.ok(stringFlux);
    }

    @GetMapping(value = "/download-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Mono<? extends Resource>> findFile(@PathVariable String fileName) {
        return ResponseEntity.ok(Mono.fromCallable(() -> menuPositionService.findFile(fileName)));
    }

}
