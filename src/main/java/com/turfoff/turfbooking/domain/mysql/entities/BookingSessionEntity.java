package com.turfoff.turfbooking.domain.mysql.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
public class BookingSessionEntity extends EntityBase {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private BookingsEntity bookingId;

    private LocalDateTime startTime;

    private int duration;
}
