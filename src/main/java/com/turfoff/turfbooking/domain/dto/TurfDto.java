package com.turfoff.turfbooking.domain.dto;

import com.turfoff.turfbooking.domain.entities.AdminEntity;
import com.turfoff.turfbooking.domain.entities.EntityBase;
import com.turfoff.turfbooking.utilities.TurfStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TurfDto extends EntityBase {
    private String name;
    private TurfStatus status;
    private AdminEntity owner;
    private AdminEntity manager;
    private int rent;
    private String amenities;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;
}