package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
import com.turfoff.turfbooking.repositories.mongo.TurfRepository;
import org.springframework.stereotype.Service;

@Service
public class TurfServiceImpl implements TurfService {

    private TurfRepository turfRepository;

    public TurfServiceImpl(TurfRepository turfRepository) {
        this.turfRepository = turfRepository;
    }

    @Override
    public TurfEntity createTurf(TurfEntity turfEntity) {
        return turfRepository.save(turfEntity);
    }

    @Override
    public TurfEntity getTurf(Long turfId) {
        return turfRepository.getReferenceById(turfId);
    }
}
