package com.school.ppmg.computer_equipment_store_system_api.dtos.attribute;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;

import java.time.LocalDateTime;

public record AttributeResponse(
        Long id,
        String name,
        AttributeDataType dataType,
        String unit,
        boolean isFilterable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}