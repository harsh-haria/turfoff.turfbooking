package com.turfoff.turfbooking.domain.mongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot {
    private LocalTime startTime;

    private LocalTime endTime;

    private boolean status;
}
