package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;

public interface TurfService {
    TurfEntity createTurf(TurfEntity turfEntity);
    TurfEntity getTurf(Long turfId);
}
