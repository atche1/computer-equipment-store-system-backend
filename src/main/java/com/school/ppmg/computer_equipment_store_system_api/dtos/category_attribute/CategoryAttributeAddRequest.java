package com.school.ppmg.computer_equipment_store_system_api.dtos.category_attribute;


import jakarta.validation.constraints.NotNull;

public record CategoryAttributeAddRequest(
        @NotNull Long attributeId
) {}
