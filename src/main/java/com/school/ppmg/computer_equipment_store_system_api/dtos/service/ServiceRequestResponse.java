package com.school.ppmg.computer_equipment_store_system_api.dtos.service;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;

import java.time.LocalDateTime;

public record ServiceRequestResponse(
        Long id,
        Long userId,
        Long serviceId,
        String serviceName,
        ServiceRequestStatus status,
        String customerPhone,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
