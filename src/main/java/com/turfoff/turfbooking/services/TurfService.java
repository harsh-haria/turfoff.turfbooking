package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;

import java.util.Optional;

public interface TurfService {
    TurfEntity createTurf(TurfEntity turfEntity);
    Optional<TurfEntity> getTurf(String turfId);
}
