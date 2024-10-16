package com.turfoff.turfbooking.repositories.mongo;

import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotsRepository extends MongoRepository<SlotsEntity, String> {
    List<SlotsEntity> findByTurfIdAndDate(String turfId, LocalDate date);
}