package com.turfoff.turfbooking;

import com.turfoff.turfbooking.domain.dto.AdminDto;
import com.turfoff.turfbooking.domain.entities.AdminEntity;
import com.turfoff.turfbooking.domain.entities.TurfEntity;
import com.turfoff.turfbooking.utilities.TurfStatus;

public class TestDataUtil {

    public TestDataUtil() {}

    public static AdminEntity createTestAdminA () {
        return AdminEntity.builder()
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

    public static TurfEntity createTestTurfEntity(Long adminId) {

        AdminEntity admin = createTestAdminA();
        admin.setId(adminId);

        return TurfEntity.builder()
                .id(1L)
                .name("Test Turf")
                .status(TurfStatus.ACTIVE)
                .owner(admin)
                .manager(admin)
                .rent(1500)
                .amenities("AM1, AM2, AM3")
                .phone("12345 67890")
                .email("turf1@turfoff.com")
                .address("Mumbai, In")
                .build();
    }

}
