package com.school.ppmg.computer_equipment_store_system_api.dtos.user;

import com.school.ppmg.computer_equipment_store_system_api.enums.Role;

import java.time.LocalDateTime;

public record MyAccountResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Role role,
        Boolean enabled,
        LocalDateTime createdAt
) {
}