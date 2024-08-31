package com.turfoff.turfbooking.domain.mongo.dto;

import com.turfoff.turfbooking.domain.mysql.entities.AdminEntity;
import com.turfoff.turfbooking.domain.mysql.entities.EntityBase;
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