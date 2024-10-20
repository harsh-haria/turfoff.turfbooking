package com.turfoff.turfbooking.repositories.mongo;

import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotsRepository extends MongoRepository<SlotsEntity, String> {
    List<SlotsEntity> findByTurfIdAndDate(String turfId, LocalDate date);
    Optional<SlotsEntity> findById(String turfId);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'slotStatus': ?1 } }")
    int updateSlotStatusById(String id, String slotStatus);
}