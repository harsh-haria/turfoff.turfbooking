package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;

import java.time.LocalDate;
import java.util.List;

public interface SlotsService {
    public List<SlotsEntity> getAllSlotsOfTurf(String turfId, LocalDate date);
}