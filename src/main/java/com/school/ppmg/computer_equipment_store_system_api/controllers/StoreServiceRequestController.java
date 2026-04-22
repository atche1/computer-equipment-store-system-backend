package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.CreateServiceRequestRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.ServiceRequestResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.UpdateServiceRequestStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.services.StoreServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-requests")
public class StoreServiceRequestController {

    private final StoreServiceRequestService storeServiceRequestService;

    @PostMapping
    public ServiceRequestResponse create(@Valid @RequestBody CreateServiceRequestRequest request) {
        return storeServiceRequestService.create(request);
    }

    @GetMapping("/my")
    public Page<ServiceRequestResponse> getMyRequests(Pageable pageable) {
        return storeServiceRequestService.getMyRequests(pageable);
    }

    @GetMapping("/my/{id}")
    public ServiceRequestResponse getMyRequestById(@PathVariable Long id) {
        return storeServiceRequestService.getMyRequestById(id);
    }

    @GetMapping
    public Page<ServiceRequestResponse> getAll(
            @RequestParam(required = false) ServiceRequestStatus status,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            Pageable pageable
    ) {
        return storeServiceRequestService.getAll(status, q, serviceId, dateFrom, dateTo, pageable);
    }

    @GetMapping("/{id}")
    public ServiceRequestResponse getById(@PathVariable Long id) {
        return storeServiceRequestService.getById(id);
    }

    @PutMapping("/{id}/status")
    public ServiceRequestResponse updateStatus(@PathVariable Long id,
                                               @Valid @RequestBody UpdateServiceRequestStatusRequest request) {
        return storeServiceRequestService.updateStatus(id, request);
    }
}