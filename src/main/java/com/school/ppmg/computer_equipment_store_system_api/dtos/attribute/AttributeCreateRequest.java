package com.school.ppmg.computer_equipment_store_system_api.dtos.attribute;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import jakarta.validation.constraints.*;

public record AttributeCreateRequest(
        @NotBlank @Size(max = 120)
        String name,

        @NotNull
        AttributeDataType dataType,

        @Size(max = 30)
        String unit,

        Boolean isFilterable
) {}
