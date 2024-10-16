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
        return slotsRepository.findByTurfIdAndDate(turfId, date);
    }

    @Override
    public Boolean slotsExistsForDate(String turfId, LocalDate date) {
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1).toLocalDate();

        return slotsRepository.findFirstByTurfIdAndDateBetween(turfId, startOfDay, endOfDay).isPresent();
    }

    @Override
    public void saveSlots(List<SlotsEntity> slots) {
        slotsRepository.saveAll(slots);
    }
}