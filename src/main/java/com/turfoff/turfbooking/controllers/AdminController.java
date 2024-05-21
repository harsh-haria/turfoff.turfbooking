package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.dto.AdminDto;
import com.turfoff.turfbooking.domain.entities.AdminEntity;
import com.turfoff.turfbooking.mappers.impl.AdminMapperImpl;
import com.turfoff.turfbooking.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private AdminService adminService;
    private AdminMapperImpl adminMapper;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminController(AdminService adminService, AdminMapperImpl adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable UUID id) {
        Optional<AdminEntity> adminRecord = adminService.findAdmin(id);
        return adminRecord.map(adminEntity -> {
            AdminDto adminDto = adminMapper.mapTo(adminEntity);
            return new ResponseEntity<>(adminDto, HttpStatus.CREATED);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping(path = "/")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        adminDto.setId(UUID.randomUUID());
        adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        AdminEntity adminEntity = adminMapper.mapFrom(adminDto);
        AdminEntity savedAdmin = adminService.saveAdmin(adminEntity);
        return new ResponseEntity<>(adminMapper.mapTo(savedAdmin), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/updatePassword")
    public ResponseEntity changeAdminPassword(@RequestBody AdminDto adminDto) {
        adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        AdminEntity adminEntity = adminMapper.mapFrom(adminDto);
        adminService.saveAdmin(adminEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
