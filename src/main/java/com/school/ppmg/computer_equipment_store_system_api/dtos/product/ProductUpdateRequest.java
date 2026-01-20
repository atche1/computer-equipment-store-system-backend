package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record ProductUpdateRequest(
        @NotBlank @Size(max = 150)
        String name,

        @Size(max = 5000)
        String description,

        @NotNull @DecimalMin("0.00")
        BigDecimal price,

        @NotNull @Min(0)
        Integer quantity,

        @NotNull
        Boolean isActive,

        @NotNull
        Long categoryId,

        List<ProductAttributeValueUpsert> attributes,
        List<ProductImageUpsert> images
) {}