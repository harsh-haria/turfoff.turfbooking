package com.turfoff.turfbooking;

import com.turfoff.turfbooking.domain.dto.AdminDto;
import com.turfoff.turfbooking.domain.entities.AdminEntity;

import java.util.UUID;

public class TestDataUtil {

    public TestDataUtil() {}

    public static AdminEntity createTestAdminA () {
        return AdminEntity.builder()
                .id(1L)
                .firstName("Harsh")
                .lastName("Haria")
                .email("test@harsh.com")
                .password("harshharia")
                .phone("8181800000")
        .build();
    }

    public static AdminDto createTestAdminDto () {
        return AdminDto.builder()
                .id(1L)
                .firstName("HarshDto")
                .lastName("HariaDto")
                .email("test@harsh.com")
                .password("harshharia")
                .phone("8181800000")
                .build();
    }

}
