package com.school.ppmg.computer_equipment_store_system_api.dtos.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServiceRequest(

        @NotBlank(message = "Service name is required")
        @Size(min = 3, max = 120, message = "Service name must be between 3 and 120 characters")
        String name,

        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
        BigDecimal price,

        @NotNull(message = "Active status is required")
        Boolean isActive
) {}