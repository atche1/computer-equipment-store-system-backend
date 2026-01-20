package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductImageUpsert(
        @NotBlank @Size(max = 1000)
        String imageUrl,

        Boolean isMain
) {}