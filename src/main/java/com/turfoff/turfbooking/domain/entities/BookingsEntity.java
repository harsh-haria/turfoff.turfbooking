package com.turfoff.turfbooking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "bookings")
public class BookingsEntity extends EntityBase{

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private TurfEntity turfId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime finishTime;
}
