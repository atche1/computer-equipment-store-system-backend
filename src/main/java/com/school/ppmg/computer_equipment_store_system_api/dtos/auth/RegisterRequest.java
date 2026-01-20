package com.school.ppmg.computer_equipment_store_system_api.dtos.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email @NotBlank @Size(max = 255)
        String email,

        @NotBlank @Size(min = 6, max = 72)
        String password,

        @NotBlank @Size(max = 50)
        String firstName,

        @NotBlank @Size(max = 50)
        String lastName,

        @NotBlank @Size(max = 20)
        String phone
) {}