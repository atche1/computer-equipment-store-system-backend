package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> items,
        BigDecimal subtotal,
        int totalItems,
        int totalQuantity
) {}