package com.turfoff.turfbooking.domain.mysql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "bookings")
public class BookingsEntity extends EntityBase {

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String turf;

    private String status;

    private float amount;

    private String coupon;

    private float discount;

    private String transactionType;

    private String generatedTransactionId;
}
