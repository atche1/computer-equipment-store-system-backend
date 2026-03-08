package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record MergeCartRequest(
        @NotEmpty Map<@NotNull Long, @NotNull @Min(1) Integer> items
) {}