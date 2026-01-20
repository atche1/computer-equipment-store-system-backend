package com.school.ppmg.computer_equipment_store_system_api.dtos.auth;

public record AuthResponse(
        String token,
        UserResponse user
) {}