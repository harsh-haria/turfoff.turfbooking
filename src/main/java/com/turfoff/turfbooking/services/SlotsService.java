package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;

import java.time.LocalDate;
import java.util.List;

public interface SlotsService {
    List<SlotsEntity> getAllSlotsOfTurf(String turfId, LocalDate date);
    Boolean slotsExistsForDate(String turfId, LocalDate date);
    void saveSlots(List<SlotsEntity> slots);
}