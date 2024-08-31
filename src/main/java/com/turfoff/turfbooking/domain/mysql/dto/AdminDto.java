package com.turfoff.turfbooking.domain.mysql.dto;

import com.turfoff.turfbooking.domain.mysql.entities.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdminDto extends EntityBase {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phone;
}
