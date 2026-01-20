package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailsResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int quantity,
        boolean isActive,
        Long categoryId,
        String categoryName,
        List<ProductImageResponse> images,
        List<ProductAttributeValueResponse> attributes
) {}