package com.turfoff.turfbooking.domain.dto;

import com.turfoff.turfbooking.domain.entities.AdminEntity;
import com.turfoff.turfbooking.utilities.TurfStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurfDto {
    private UUID id;
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