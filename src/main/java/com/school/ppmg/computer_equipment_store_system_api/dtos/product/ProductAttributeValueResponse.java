package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;

import java.math.BigDecimal;

public record ProductAttributeValueResponse(
        Long attributeId,
        String attributeName,
        AttributeDataType dataType,
        String unit,
        String valueText,
        BigDecimal valueNumber,
        Boolean valueBoolean
) {}
