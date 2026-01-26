package com.school.ppmg.computer_equipment_store_system_api.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 150) String slug,
        Boolean isActive
) {}