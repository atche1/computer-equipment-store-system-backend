package com.school.ppmg.computer_equipment_store_system_api.dtos.cart;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal,
        String imageUrl
) {}
