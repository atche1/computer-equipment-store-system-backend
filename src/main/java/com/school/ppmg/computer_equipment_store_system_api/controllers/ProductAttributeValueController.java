package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value.ProductAttributeValueRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value.ProductAttributeValueResponse;
import com.school.ppmg.computer_equipment_store_system_api.services.ProductAttributeValueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/attribute-values")
public class ProductAttributeValueController {

    private final ProductAttributeValueService pavService;

    public ProductAttributeValueController(ProductAttributeValueService pavService) {
        this.pavService = pavService;
    }

    @GetMapping
    public List<ProductAttributeValueResponse> list(@PathVariable Long productId) {
        return pavService.listByProduct(productId);
    }

    @PutMapping
    public List<ProductAttributeValueResponse> upsertBatch(
            @PathVariable Long productId,
            @Valid @RequestBody List<ProductAttributeValueRequest> requests
    ) {
        return pavService.upsertBatch(productId, requests);
    }

    @DeleteMapping("/{pavId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long productId, @PathVariable Long pavId) {
        pavService.deleteOne(productId, pavId);
    }
}