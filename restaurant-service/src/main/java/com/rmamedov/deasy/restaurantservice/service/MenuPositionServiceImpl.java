package com.rmamedov.deasy.restaurantservice.service;

import com.rmamedov.deasy.restaurantservice.config.data.DataProperties;
import com.rmamedov.deasy.restaurantservice.converter.MenuPositionToMenuPositionDTOConverter;
import com.rmamedov.deasy.restaurantservice.exception.MenuPositionNotFoundException;
import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.model.dto.UploadDetailsDTO;
import com.rmamedov.deasy.restaurantservice.repository.MenuPositionRepository;
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
public class MenuPositionServiceImpl implements MenuPositionService {

    private static final String POSITION_NOT_FOUND_MSG = "MenuPosition with id: '%s' - Not Found.";
    private static final String IMAGES_SUCCESSFULLY_ADDED_MSG = "File: '{}' was uploaded and added to MenuPosition with id: '{}', images: '{}'";

    private final DataProperties dataProperties;

    private final MenuPositionRepository positionRepository;

    private final MenuPositionToMenuPositionDTOConverter menuPositionDTOConverter;

    @Override
    @Transactional
    public Mono<MenuPositionDTO> save(final MenuPosition position) {
        return positionRepository.save(position.setAsNew())
                .map(menuPositionDTOConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<MenuPositionDTO> findById(final String id) {
        return positionRepository.findById(id)
                .map(menuPositionDTOConverter::convert)
                .switchIfEmpty(Mono.error(new MenuPositionNotFoundException(String.format(POSITION_NOT_FOUND_MSG, id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<MenuPositionDTO> findAll() {
        return positionRepository.findAll()
                .map(menuPositionDTOConverter::convert);
    }

    @Override
    @Transactional
    public Mono<UploadDetailsDTO> uploadAndAdd(final FilePart filePart, final String positionId) {
        return positionRepository.findById(positionId)
                .flatMap(position -> {
                    createDirectoryIfNotExists();
                    final String filename = filePart.filename();
                    final Path path = Paths.get(addImageName(filename, position));
                    filePart.transferTo(path);
                    return positionRepository.save(position)
                            .then(Mono.just(new UploadDetailsDTO(filename)))
                            .doOnSuccess(success -> log.info(IMAGES_SUCCESSFULLY_ADDED_MSG, filename, position.getId(), position.getImages()));
                })
                .switchIfEmpty(Mono.error(new MenuPositionNotFoundException(String.format(POSITION_NOT_FOUND_MSG, positionId))));
    }

    @Override
    public Resource findFile(final String fileName) {
        final String fullyPath = dataProperties.getDirectory() + fileName;
        Assert.isTrue(new File(fullyPath).exists(), String.format("File '%s' is not exists.", fullyPath));
        final Path path = Paths.get(fullyPath);
        return new FileSystemResource(path);
    }

    private String addImageName(final String fileName, final MenuPosition menuPosition) {
        menuPosition.getImages().add(fileName);
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
