package com.school.ppmg.computer_equipment_store_system_api.dtos.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest
        (
                @NotBlank(message = "Email is required.")
                @Email(message = "Please enter a valid email address.")
                String email,

                @NotBlank(message = "Password is required.")
                String password
        ) {}