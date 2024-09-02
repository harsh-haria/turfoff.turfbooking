package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.mongo.dto.TurfDto;
import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
import com.turfoff.turfbooking.mappers.impl.TurfMapperImpl;
import com.turfoff.turfbooking.services.TurfService;
import com.turfoff.turfbooking.utilities.TurfStatus;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity getTurfById(@PathVariable final String id) {
        Optional<TurfEntity> turfEntity = turfService.getTurf(id);
        if (turfEntity.isPresent()) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Successfully fetched turf with id " + id);
            map.put("data", turfMapper.mapTo(turfEntity.get()));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<TurfEntity> updateTurf(@PathVariable String id, @RequestBody final TurfDto turfDto) {
        turfDto.setId(id);
//        turfDto.setLastModifiedAt(LocalDateTime.now());
        TurfEntity turfEntity = turfMapper.mapFrom(turfDto);
        TurfEntity createdTurfEntity = turfService.createTurf(turfEntity);
        return new ResponseEntity<>(createdTurfEntity, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path = "/getNearByTurfs")
    public ResponseEntity getNearByTurfs(@RequestParam String latitude, @RequestParam String longitude, @RequestParam String radiusInKm) {
        double latitudeCoordinate = Double.parseDouble(latitude);
        double longitudeCoordinate = Double.parseDouble(longitude);
        double radius = Double.parseDouble(radiusInKm);

        Point point = new Point(latitudeCoordinate, longitudeCoordinate);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        List<TurfEntity> turfList = turfService.getNearByTurfs(point, distance);
        return new ResponseEntity<>(turfList, HttpStatus.OK);
    }
}
