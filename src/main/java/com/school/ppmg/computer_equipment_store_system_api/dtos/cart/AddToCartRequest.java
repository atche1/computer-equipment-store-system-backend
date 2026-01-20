package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import jakarta.validation.constraints.*;

public record AddToCartRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {}