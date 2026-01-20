package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import java.math.BigDecimal;

public record ProductListItemResponse(
        Long id,
        String name,
        BigDecimal price,
        int quantity,
        boolean isActive,
        Long categoryId,
        String categoryName,
        String mainImageUrl
) {}