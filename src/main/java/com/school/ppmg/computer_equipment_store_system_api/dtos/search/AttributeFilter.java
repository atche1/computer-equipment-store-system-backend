package com.school.ppmg.computer_equipment_store_system_api.dtos.search;

import java.math.BigDecimal;

public record AttributeFilter(
        Long attributeId,
        String textEquals,
        BigDecimal numberMin,
        BigDecimal numberMax,
        Boolean booleanEquals
) {}