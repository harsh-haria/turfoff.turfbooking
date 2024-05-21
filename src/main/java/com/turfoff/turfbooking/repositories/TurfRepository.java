package com.turfoff.turfbooking.repositories;

import com.turfoff.turfbooking.domain.entities.TurfEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TurfRepository extends CrudRepository<TurfEntity, UUID> {
}
