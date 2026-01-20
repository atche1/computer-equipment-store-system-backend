package com.school.ppmg.computer_equipment_store_system_api.dtos.search;

import java.math.BigDecimal;
import java.util.List;

public record ProductSearchRequest(
        String q,
        Long categoryId,
        BigDecimal priceMin,
        BigDecimal priceMax,
        Boolean inStockOnly,
        String brand,
        List<AttributeFilter> filters,
        Integer page,
        Integer size,
        String sort
) {}
