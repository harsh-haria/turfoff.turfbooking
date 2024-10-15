package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.mongo.dto.TurfDto;
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

    public TurfController(TurfService turfService, TurfMapperImpl turfMapper, SlotsService slotsService) {
        this.turfService = turfService;
        this.turfMapper = turfMapper;
        this.slotsService = slotsService;
    }

    List<TimeSlot> generateTimingsForSlotsWithDuration(LocalTime openingTime, int slotDuration) {
        LocalTime entryTime = openingTime;
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalTime slotStartTime = openingTime;
        LocalTime slotEndTime = slotStartTime.plusMinutes(slotDuration);

        int safetyCounter = 120;
        int slotCounter = 0;
        while (true) {
            timeSlots.add(new TimeSlot(slotStartTime, slotEndTime));
            slotStartTime = slotEndTime;
            slotEndTime = slotStartTime.plusMinutes(slotDuration);
            if (slotCounter == safetyCounter) {
                break;
            }
            if (slotEndTime.equals(entryTime)) {
                timeSlots.add(new TimeSlot(slotStartTime, slotEndTime));
                break;
            }
            slotCounter++;
        }
        return timeSlots;
    }

//    boolean isOverlapping(LocalTime slotStart, LocalTime slotEnd, LocalTime bookingStart, LocalTime bookingEnd) {
//        return (slotStart.isBefore(bookingEnd) && slotEnd.isAfter(bookingStart));
//    }

//    void markBookedSlots(List<TimeSlot> slots, List<BookingSessionEntity> bookings) {
//        for (BookingSessionEntity booking : bookings) {
//            for (TimeSlot slot : slots) {
//                // Check if the slot overlaps with a booking
//                if (isOverlapping(slot.getStartTime(), slot.getEndTime(), booking.getStartTime(), booking.getStartTime().plusMinutes(booking.getDuration()))) {
//                    slot.setStatus(true); // Mark as booked
//                }
//            }
//        }
//    }

    List<SlotsEntity> generateTurfSlots(TurfEntity turfEntity, LocalDate date) {
        String turfId = turfEntity.getId();
        int slotDuration = turfEntity.getSlotDuration();
        int startHour = turfEntity.getStartHour();
        int endHour = turfEntity.getEndHour();

        int numDayOfWeek = date.getDayOfWeek().getValue();

        LocalDate startDate = date.minusDays(numDayOfWeek-1);
        LocalDate endDate = startDate.plusDays(7);

        List<TimeSlot> timeSlots = generateTimingsForSlotsWithDuration(
                LocalTime.of(startHour, 0), slotDuration
        );

        List<SlotsEntity> daySlots = new ArrayList<>();
        while(startDate.isBefore(endDate)) {
            //check if the date already exists in the database
            Boolean slotsExistsForDate = slotsService.slotsExistsForDate(turfId, startDate);

            //if yes then skip
            if(slotsExistsForDate) {
                continue;
            }

            // else generate the slots and insert
            List<SlotsEntity> slots = new ArrayList<>();
            for (TimeSlot timeslot : timeSlots) {
                SlotsEntity slot = SlotsEntity.builder()
                        .turfId(turfId)
                        .date(startDate)
                        .slot(new TimeSlot(timeslot.getStartTime(), timeslot.getEndTime()))
                        .slotStatus(SlotStatus.VACANT)
                        .build();
                slots.add(slot);
            }
            // we save the slots we want so that we can return them to the user.
            if (startDate.isEqual(date)) {
                daySlots.addAll(slots);
            }

            // save the slots in the database.
            slotsService.saveSlots(slots);

            // increment for the loop
            startDate = startDate.plusDays(1);
        }
        return daySlots;
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

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/getSlots")
    public ResponseEntity getTurfSlots(@RequestParam String turfId, @RequestParam String dateString) throws Exception {
        try {
            LocalDate date = LocalDate.parse(dateString);
            Optional<TurfEntity> turf = turfService.getTurf(turfId);
            if (turf.isPresent()) {
                TurfEntity turfEntity = turf.get();

                List<SlotsEntity> availableSlots = slotsService.getAllSlotsOfTurf(turfId, date);

                if (availableSlots.isEmpty()) {
                    availableSlots = generateTurfSlots(turfEntity, date);
                }

                return new ResponseEntity<>(availableSlots, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
