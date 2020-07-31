package com.rmamedov.deasy.restaurantservice.service;

import com.rmamedov.deasy.restaurantservice.converter.MenuPositionToMenuPositionDTOConverter;
import com.rmamedov.deasy.restaurantservice.exception.MenuPositionNotFoundException;
import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import com.rmamedov.deasy.restaurantservice.model.dto.MenuPositionDTO;
import com.rmamedov.deasy.restaurantservice.repository.MenuPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuPositionServiceImpl implements MenuPositionService {

    private final MenuPositionRepository positionRepository;

    private final MenuPositionToMenuPositionDTOConverter menuPositionDTOConverter;

    @Override
    @Transactional
    public Mono<MenuPositionDTO> save(final MenuPosition position) {
        return positionRepository.save(position)
                .map(menuPositionDTOConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<MenuPositionDTO> findById(final String id) {
        return positionRepository.findById(id)
                .map(menuPositionDTOConverter::convert)
                .switchIfEmpty(Mono.error(new MenuPositionNotFoundException(String.format("MenuPosition with id: '%s' - Not Found.", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<MenuPositionDTO> findAll() {
        return positionRepository.findAll()
                .map(menuPositionDTOConverter::convert);
    }

}
