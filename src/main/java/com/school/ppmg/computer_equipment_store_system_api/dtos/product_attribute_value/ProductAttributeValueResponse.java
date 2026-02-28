package com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;

import java.math.BigDecimal;

public record ProductAttributeValueResponse(
        Long id,
        Long productId,

        Long attributeId,
        String attributeName,
        AttributeDataType dataType,
        String unit,
        Boolean filterable,

        String valueText,
        BigDecimal valueNumber,
        Boolean valueBoolean
) {}
