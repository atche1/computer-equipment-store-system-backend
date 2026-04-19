package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceResponse;
import com.school.ppmg.computer_equipment_store_system_api.services.StoreServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class StoreServiceController {

    private final StoreServiceService storeServiceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse create(@Valid @RequestBody ServiceRequest request) {
        return storeServiceService.create(request);
    }

    @GetMapping("/{id}")
    public ServiceResponse getById(@PathVariable Long id) {
        return storeServiceService.getById(id);
    }

    @GetMapping("/active")
    public Page<ServiceResponse> getAllActive(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable
    ) {
        return storeServiceService.getAllActive(q, minPrice, maxPrice, pageable);
    }

    @GetMapping("/active-list")
    public List<ServiceResponse> getAllActiveList() {
        return storeServiceService.getAllActive();
    }

    @GetMapping
    public Page<ServiceResponse> getAllForAdmin(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable
    ) {
        return storeServiceService.getAllForAdmin(q, isActive, minPrice, maxPrice, pageable);
    }

    @PutMapping("/{id}")
    public ServiceResponse update(@PathVariable Long id,
                                  @Valid @RequestBody ServiceRequest request) {
        return storeServiceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeServiceService.delete(id);
    }
}