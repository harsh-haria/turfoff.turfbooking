package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.dto.TurfDto;
import com.turfoff.turfbooking.domain.entities.TurfEntity;
import com.turfoff.turfbooking.mappers.impl.TurfMapperImpl;
import com.turfoff.turfbooking.services.TurfService;
import com.turfoff.turfbooking.utilities.TurfStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/turfs")
public class TurfController {
    private TurfMapperImpl turfMapper;
    private TurfService turfService;

    public TurfController(TurfService turfService, TurfMapperImpl turfMapper) {
        this.turfService = turfService;
        this.turfMapper = turfMapper;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping(path = "/new")
    public ResponseEntity<TurfEntity> createTurf(@RequestBody final TurfDto turfDto) {
        turfDto.setStatus(TurfStatus.INACTIVE);
        turfDto.setCreatedAt(LocalDateTime.now());
        TurfEntity turfEntity = turfMapper.mapFrom(turfDto);
        TurfEntity createdTurfEntity = turfService.createTurf(turfEntity);
        return new ResponseEntity<>(createdTurfEntity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity getTurfById(@PathVariable final Long id) {
        TurfEntity turfEntity = turfService.getTurf(id);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Successfully fetched turf with id " + id);
        map.put("data", turfMapper.mapTo(turfEntity));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<TurfEntity> updateTurf(@PathVariable Long id, @RequestBody final TurfDto turfDto) {
        turfDto.setId(id);
        turfDto.setLastModifiedAt(LocalDateTime.now());
        TurfEntity turfEntity = turfMapper.mapFrom(turfDto);
        TurfEntity createdTurfEntity = turfService.createTurf(turfEntity);
        return new ResponseEntity<>(createdTurfEntity, HttpStatus.ACCEPTED);
    }
}
