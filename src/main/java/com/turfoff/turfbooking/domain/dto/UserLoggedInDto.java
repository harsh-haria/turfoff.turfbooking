package com.turfoff.turfbooking.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedInDto {
    private String username;
    private String token;
    private List<String> roles;
}
