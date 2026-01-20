package com.school.ppmg.computer_equipment_store_system_api.dtos.category;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String slug,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}