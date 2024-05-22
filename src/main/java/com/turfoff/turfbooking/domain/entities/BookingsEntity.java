package com.turfoff.turfbooking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class BookingsEntity {
    @Id
    @Column(unique = true, nullable = false)
    private UUID bookingId;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
