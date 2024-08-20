package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.dto.AdminDto;
import com.turfoff.turfbooking.domain.entities.AdminEntity;

import java.util.Optional;

public interface AdminService {
    Optional<AdminEntity> findAdmin(Long id);

    AdminEntity saveAdmin(AdminEntity adminEntity);

    void updatePassword(AdminDto adminDto);

    void updatePhoneNumber(AdminDto adminDto);
}
