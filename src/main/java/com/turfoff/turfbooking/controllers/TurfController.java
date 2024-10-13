package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.mongo.dto.TurfDto;
import com.turfoff.turfbooking.domain.mongo.entities.BookingSessionEntity;
import com.turfoff.turfbooking.domain.mongo.entities.SlotsEntity;
import com.turfoff.turfbooking.domain.mongo.entities.TimeSlot;
import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
import com.turfoff.turfbooking.mappers.impl.TurfMapperImpl;
import com.turfoff.turfbooking.services.SlotsService;
import com.turfoff.turfbooking.services.TurfService;
import com.turfoff.turfbooking.utilities.SlotStatus;
import com.turfoff.turfbooking.utilities.TurfStatus;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping(path = "/turfs")
public class TurfController {
    private TurfMapperImpl turfMapper;
    private TurfService turfService;
    private SlotsService slotsService;

    public TurfController(TurfService turfService, TurfMapperImpl turfMapper) {
        this.turfService = turfService;
        this.turfMapper = turfMapper;
    }

    List<TimeSlot> generateTimeSlots(LocalTime openingTime, LocalTime closingTime, int slotDuration) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        LocalTime slotStartTime = openingTime;

        while (slotStartTime.isBefore(closingTime)) {
            LocalTime slotEndTime = slotStartTime.plusMinutes(slotDuration);
            timeSlots.add(new TimeSlot(slotStartTime, slotEndTime, false)); // false means 'not booked'
            slotStartTime = slotEndTime;
        }

        return timeSlots;
    }

    boolean isOverlapping(LocalTime slotStart, LocalTime slotEnd, LocalTime bookingStart, LocalTime bookingEnd) {
        return (slotStart.isBefore(bookingEnd) && slotEnd.isAfter(bookingStart));
    }

    void markBookedSlots(List<TimeSlot> slots, List<BookingSessionEntity> bookings) {
        for (BookingSessionEntity booking : bookings) {
            for (TimeSlot slot : slots) {
                // Check if the slot overlaps with a booking
                if (isOverlapping(slot.getStartTime(), slot.getEndTime(), booking.getStartTime(), booking.getStartTime().plusMinutes(booking.getDuration()))) {
                    slot.setStatus(true); // Mark as booked
                }
            }
        }
    }

    List<SlotsEntity> generateTurfSlots (TurfEntity turfEntity) {
        String turfId = turfEntity.getId();
        int slotDuration = turfEntity.getSlotDuration();
        int startHour = turfEntity.getStartHour();
        int endHour = turfEntity.getEndHour();

        List<TimeSlot> timeSlots = generateTimeSlots(
                LocalTime.of(startHour, 0), LocalTime.of(endHour, 59), slotDuration
        );

        List<SlotsEntity> slots = new ArrayList<>();
        for (TimeSlot timeslot : timeSlots) {
            SlotsEntity slot = SlotsEntity.builder()
                    .turfId(turfId)
                    .slotDuration(slotDuration)
                    .slotStatus(SlotStatus.VACANT)
                    .build();
            slots.add(slot);
        }

        return slots;
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

    @GetMapping("/getSlots")
    public ResponseEntity getTurfSlots(@RequestParam String id, @RequestParam LocalDate date) {
        Optional<TurfEntity> turf = turfService.getTurf(id);
        if (turf.isPresent()) {
            TurfEntity turfEntity = turf.get();

            List<SlotsEntity> availableSlots = slotsService.getAllSlotsOfTurf(id, date);

            if(availableSlots.isEmpty()) {
                generateTurfSlots(turfEntity);
            }

            // return the slots for the users to choose to book them

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
