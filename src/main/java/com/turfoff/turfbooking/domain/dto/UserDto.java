package com.turfoff.turfbooking.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private int points;
    private String refUsed;
    private String refferalCode;
}
