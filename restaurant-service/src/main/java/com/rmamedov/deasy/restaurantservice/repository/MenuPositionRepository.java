package com.rmamedov.deasy.restaurantservice.repository;

import com.rmamedov.deasy.restaurantservice.model.MenuPosition;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPositionRepository extends ReactiveSortingRepository<MenuPosition, String> {
}
