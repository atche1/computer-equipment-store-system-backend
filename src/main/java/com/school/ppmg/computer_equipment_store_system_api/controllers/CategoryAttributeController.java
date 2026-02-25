package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.category_attribute.CategoryAttributeAddRequest;
import com.school.ppmg.computer_equipment_store_system_api.services.CategoryAttributeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/{categoryId}/attributes")
public class CategoryAttributeController {

    private final CategoryAttributeService categoryAttributeService;

    public CategoryAttributeController(CategoryAttributeService categoryAttributeService) {
        this.categoryAttributeService = categoryAttributeService;
    }

    @GetMapping
    public List<AttributeResponse> list(@PathVariable Long categoryId) {
        return categoryAttributeService.listAttributes(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttributeResponse add(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryAttributeAddRequest request
    ) {
        return categoryAttributeService.addAttribute(categoryId, request.attributeId());
    }

    @DeleteMapping("/{attributeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(
            @PathVariable Long categoryId,
            @PathVariable Long attributeId
    ) {
        categoryAttributeService.removeAttribute(categoryId, attributeId);
    }
}