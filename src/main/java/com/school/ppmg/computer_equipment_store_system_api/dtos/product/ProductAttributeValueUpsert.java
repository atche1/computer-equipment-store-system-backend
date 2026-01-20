package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductAttributeValueUpsert(
        @NotNull
        Long attributeId,

        @NotNull
        AttributeDataType dataType,

        String valueText,
        BigDecimal valueNumber,
        Boolean valueBoolean
) {}