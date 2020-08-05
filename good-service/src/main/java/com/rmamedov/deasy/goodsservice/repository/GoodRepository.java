package com.rmamedov.deasy.goodsservice.repository;

import com.rmamedov.deasy.goodsservice.model.Good;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends ReactiveSortingRepository<Good, String> {
}
