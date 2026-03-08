package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
        @NotNull @Min(0) Integer quantity
) {}