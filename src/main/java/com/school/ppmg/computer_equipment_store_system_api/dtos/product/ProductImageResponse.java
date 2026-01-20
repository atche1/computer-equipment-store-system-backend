package com.school.ppmg.computer_equipment_store_system_api.dtos.product;

public record ProductImageResponse(
        Long id,
        String imageUrl,
        boolean isMain
) {}