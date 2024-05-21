package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.entities.AdminEntity;
import com.turfoff.turfbooking.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<AdminEntity> findAdmin(UUID id) {
        return adminRepository.findById(id);
    }

    @Override
    public AdminEntity saveAdmin(AdminEntity adminEntity) {
        return adminRepository.save(adminEntity);
    }
}
