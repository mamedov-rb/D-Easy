package com.rmamedov.deasy.goodsservice.service;

import com.rmamedov.deasy.goodsservice.config.data.DataProperties;
import com.rmamedov.deasy.goodsservice.converter.GoodToGoodDTOConverter;
import com.rmamedov.deasy.goodsservice.exception.GoodNotFoundException;
import com.rmamedov.deasy.goodsservice.model.Good;
import com.rmamedov.deasy.goodsservice.model.dto.GoodDTO;
import com.rmamedov.deasy.goodsservice.model.dto.UploadDetailsDTO;
import com.rmamedov.deasy.goodsservice.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private static final String GOOD_NOT_FOUND_MSG = "Good with id: '%s' - Not Found.";
    private static final String IMAGES_SUCCESSFULLY_ADDED_MSG = "File: '{}' was uploaded and added to Good with id: '{}', images: '{}'";

    private final DataProperties dataProperties;

    private final GoodRepository goodRepository;

    private final GoodToGoodDTOConverter goodToGoodDTOConverter;

    @Override
    @Transactional
    public Mono<GoodDTO> save(final Good position) {
        return goodRepository.save(position.setAsNew())
                .map(goodToGoodDTOConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<GoodDTO> findById(final String id) {
        return goodRepository.findById(id)
                .map(goodToGoodDTOConverter::convert)
                .switchIfEmpty(Mono.error(new GoodNotFoundException(String.format(GOOD_NOT_FOUND_MSG, id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<GoodDTO> findAll() {
        return goodRepository.findAll()
                .map(goodToGoodDTOConverter::convert);
    }

    @Override
    @Transactional
    public Mono<UploadDetailsDTO> uploadAndAdd(final FilePart filePart, final String positionId) {
        return goodRepository.findById(positionId)
                .flatMap(position -> {
                    createDirectoryIfNotExists();
                    final String filename = filePart.filename();
                    final Path path = Paths.get(addImageName(filename, position));
                    filePart.transferTo(path);
                    return goodRepository.save(position)
                            .then(Mono.just(new UploadDetailsDTO(filename)))
                            .doOnSuccess(success -> log.info(IMAGES_SUCCESSFULLY_ADDED_MSG, filename, position.getId(), position.getImages()));
                })
                .switchIfEmpty(Mono.error(new GoodNotFoundException(String.format(GOOD_NOT_FOUND_MSG, positionId))));
    }

    @Override
    public Resource findFile(final String fileName) {
        final String fullyPath = dataProperties.getDirectory() + fileName;
        Assert.isTrue(new File(fullyPath).exists(), String.format("File '%s' is not exists.", fullyPath));
        final Path path = Paths.get(fullyPath);
        return new FileSystemResource(path);
    }

    private String addImageName(final String fileName, final Good good) {
        good.getImages().add(fileName);
        return dataProperties.getDirectory() + fileName;
    }

    private void createDirectoryIfNotExists() {
        final File directory = new File(dataProperties.getDirectory());
        if (!directory.exists()) {
            directory.mkdirs();
            log.info("Created directory: '{}'", dataProperties.getDirectory());
        }
    }

}
