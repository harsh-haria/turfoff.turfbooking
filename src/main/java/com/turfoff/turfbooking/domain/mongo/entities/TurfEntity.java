package com.turfoff.turfbooking.domain.mongo.entities;

import com.turfoff.turfbooking.domain.mysql.entities.AdminEntity;
import com.turfoff.turfbooking.domain.mysql.entities.EntityBase;
import com.turfoff.turfbooking.utilities.TurfStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "turfs")
public class TurfEntity extends EntityBase{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private TurfStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AdminEntity owner;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private AdminEntity manager;

    @Column(nullable = false)
    private int rent;

    private String amenities;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;
}
