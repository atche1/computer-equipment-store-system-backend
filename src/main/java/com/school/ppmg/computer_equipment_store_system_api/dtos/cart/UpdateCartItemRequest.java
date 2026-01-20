package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import jakarta.validation.constraints.*;

public record UpdateCartItemRequest(
        @NotNull @Min(1) Integer quantity
) {}