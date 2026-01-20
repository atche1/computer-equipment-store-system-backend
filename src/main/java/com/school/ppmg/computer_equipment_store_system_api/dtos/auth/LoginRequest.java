package com.school.ppmg.computer_equipment_store_system_api.dtos.auth;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @Email @NotBlank
        String email,

        @NotBlank
        String password
) {}