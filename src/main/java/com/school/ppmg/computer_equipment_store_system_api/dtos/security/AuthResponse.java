package com.school.ppmg.computer_equipment_store_system_api.dtos.security;

public record AuthResponse(
        String accessToken,
        String role
) {}