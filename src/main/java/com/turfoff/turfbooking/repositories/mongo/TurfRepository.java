package com.turfoff.turfbooking.repositories.mongo;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurfRepository extends JpaRepository<TurfEntity, Long> {
}
