package com.rmamedov.deasy.goodsservice.controller;

import com.rmamedov.deasy.goodsservice.model.Good;
import com.rmamedov.deasy.goodsservice.model.dto.GoodDTO;
import com.rmamedov.deasy.goodsservice.model.dto.UploadDetailsDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GoodsController {

    ResponseEntity<Mono<GoodDTO>> findById(String id);

    ResponseEntity<Flux<GoodDTO>> findAll();

    ResponseEntity<Mono<GoodDTO>> save(Good good);

    ResponseEntity<Flux<UploadDetailsDTO>> save(final Flux<FilePart> filePartMono, final String positionId);

    ResponseEntity<Mono<? extends Resource>> findFile(String fileName);

}
