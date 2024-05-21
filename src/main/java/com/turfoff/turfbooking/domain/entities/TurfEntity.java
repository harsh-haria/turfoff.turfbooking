package com.turfoff.turfbooking.domain.entities;

import com.turfoff.turfbooking.utilities.TurfStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "turfs")
public class TurfEntity {
    @Id
    private UUID id;
    private String name;
    private TurfStatus status;
    private UUID owner;
    private UUID manager;
    private int rent;
    private String amenities;
    private String phone;
    private String email;
    private String address;
}
