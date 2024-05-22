package com.turfoff.turfbooking.domain.entities;

import com.turfoff.turfbooking.utilities.TurfStatus;
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
@Table(name = "turfs")
public class TurfEntity {
    @Id
    @Column(unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private TurfStatus status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AdminEntity owner;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AdminEntity manager;

    @Column(nullable = false)
    private int rent;

    private String amenities;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
