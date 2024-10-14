package com.turfoff.turfbooking.domain.mysql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private int points;

    private String refUsed;

    @Column(unique = true)
    private String referralCode;

    @Column(nullable = false)
    private Roles role;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;


}
