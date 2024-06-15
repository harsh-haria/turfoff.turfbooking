package com.turfoff.turfbooking.repositories;

import com.turfoff.turfbooking.domain.entities.TurfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TurfRepository extends JpaRepository<TurfEntity, Long> {
}
