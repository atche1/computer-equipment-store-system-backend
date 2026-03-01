package com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductAttributeValueRequest(
        @NotNull Long attributeId,
        String valueText,
        BigDecimal valueNumber,
        Boolean valueBoolean
) {}