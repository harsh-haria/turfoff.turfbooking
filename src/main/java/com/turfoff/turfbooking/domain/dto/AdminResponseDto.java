package com.turfoff.turfbooking.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponseDto {
    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
}
