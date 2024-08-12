package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.dto.TurfDto;
import com.turfoff.turfbooking.domain.entities.TurfEntity;
import com.turfoff.turfbooking.mappers.impl.TurfMapperImpl;
import com.turfoff.turfbooking.services.TurfService;
import com.turfoff.turfbooking.utilities.TurfStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/turf")
public class TurfController {
    private TurfMapperImpl turfMapper;
    private TurfService turfService;

    public TurfController(TurfService turfService, TurfMapperImpl turfMapper) {
        this.turfService = turfService;
        this.turfMapper = turfMapper;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping(path = "/createTurf")
    public ResponseEntity<TurfEntity> createTurf(@RequestBody final TurfDto turfDto) {
        turfDto.setStatus(TurfStatus.INACTIVE);
        turfDto.setCreatedAt(LocalDateTime.now());
        TurfEntity turfEntity = turfMapper.mapFrom(turfDto);
        TurfEntity createdTurfEntity = turfService.createTurf(turfEntity);
        return new ResponseEntity<>(createdTurfEntity, HttpStatus.CREATED);
    }
}
