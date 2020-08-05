package com.rmamedov.deasy.goodsservice.service;

import com.rmamedov.deasy.goodsservice.model.Good;
import com.rmamedov.deasy.goodsservice.model.dto.GoodDTO;
import com.rmamedov.deasy.goodsservice.model.dto.UploadDetailsDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GoodService {

    Mono<GoodDTO> save(Good position);

    Mono<GoodDTO> findById(String id);

    Flux<GoodDTO> findAll();

    Mono<UploadDetailsDTO> uploadAndAdd(FilePart filePartFlux, String positionId);

    Resource findFile(final String fileName);

}
