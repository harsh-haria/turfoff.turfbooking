package com.turfoff.turfbooking;

import com.turfoff.turfbooking.domain.dto.AdminDto;
import com.turfoff.turfbooking.domain.entities.AdminEntity;

import java.util.UUID;

public class TestDataUtil {

    public TestDataUtil() {}

    public static AdminEntity createTestAdminA () {
        return AdminEntity.builder()
                .id(UUID.fromString("e1397d31-a1d3-498f-b809-493940d31c45"))
                .firstName("Harsh")
                .lastName("Haria")
                .email("test@harsh.com")
                .password("harshharia")
                .phone("8181800000")
        .build();
    }

    public static AdminDto createTestAdminDto () {
        return AdminDto.builder()
                .id(UUID.fromString("e1397d31-a1d3-498f-b809-493940d31c45"))
                .firstName("HarshDto")
                .lastName("Haria")
                .email("test@harsh.com")
                .password("harshharia")
                .phone("8181800000")
                .build();
    }

}
