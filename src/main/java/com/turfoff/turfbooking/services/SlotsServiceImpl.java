package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;
import com.turfoff.turfbooking.repositories.mongo.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotsServiceImpl implements SlotsService {

    @Autowired
    private SlotsRepository slotsRepository;

    @Override
    public List<SlotsEntity> getAllSlotsOfTurf(String turfId, LocalDate date) {
        return List.of();
    }
}