package com.school.ppmg.computer_equipment_store_system_api.dtos.auth;


import com.school.ppmg.computer_equipment_store_system_api.enums.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Role role,
        boolean enabled,
        LocalDateTime createdAt
) {}