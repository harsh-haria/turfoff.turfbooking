package com.turfoff.turfbooking.domain.mysql.entities;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
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
    private UserEntity user;

    private String turf;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime finishTime;
}
