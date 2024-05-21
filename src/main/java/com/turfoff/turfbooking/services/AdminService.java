package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.entities.AdminEntity;

import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    Optional<AdminEntity> findAdmin(UUID id);

    AdminEntity saveAdmin(AdminEntity adminEntity);
}
