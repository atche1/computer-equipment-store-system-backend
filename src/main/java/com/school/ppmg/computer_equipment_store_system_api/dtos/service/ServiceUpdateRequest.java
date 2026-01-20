package com.school.ppmg.computer_equipment_store_system_api.dtos.service;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ServiceUpdateRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 5000) String description,
        @NotNull @DecimalMin("0.00") BigDecimal price,
        @NotNull Boolean isActive
) {}