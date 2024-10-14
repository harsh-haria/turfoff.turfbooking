package com.turfoff.turfbooking.domain.mongo.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
}
