package com.school.ppmg.computer_equipment_store_system_api.dtos.service_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateServiceRequestRequest(

        @NotNull(message = "Service id is required")
        Long serviceId,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number")
        String customerPhone,

        @NotBlank(message = "Description is required")
        @Size(max = 5000, message = "Description must be up to 5000 characters")
        String description
) {}