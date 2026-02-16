package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeResponse;
import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import com.school.ppmg.computer_equipment_store_system_api.services.AttributeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttributeResponse create(@Valid @RequestBody AttributeRequest request) {
        return attributeService.create(request);
    }

    @GetMapping("/{id}")
    public AttributeResponse getById(@PathVariable Long id) {
        return attributeService.getById(id);
    }

    @GetMapping
    public Page<AttributeResponse> getAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) AttributeDataType dataType,
            @RequestParam(required = false) Boolean filterable,
            @RequestParam(required = false) String unit,
            Pageable pageable
    ) {
        return attributeService.search(q, dataType, filterable, unit, pageable);
    }

    @PutMapping("/{id}")
    public AttributeResponse update(@PathVariable Long id, @Valid @RequestBody AttributeRequest request) {
        return attributeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        attributeService.delete(id);
    }
}