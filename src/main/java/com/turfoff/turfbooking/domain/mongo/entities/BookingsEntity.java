package com.turfoff.turfbooking.domain.mongo.entities;

import com.turfoff.turfbooking.domain.mysql.entities.EntityBase;
import com.turfoff.turfbooking.domain.mysql.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Document(collection = "bookings")
public class BookingsEntity {
    @Id
    private String id;

    private String user;

    private String turf;

    private String status;

    private float amount;

    private String coupon;

    private float discount;

    private LocalDateTime bookingDateTime;

    private String transactionType;

    private String generatedTransactionId;
}
