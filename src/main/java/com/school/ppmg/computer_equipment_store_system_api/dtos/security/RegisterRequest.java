package com.school.ppmg.computer_equipment_store_system_api.dtos.security;

public record RegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {}