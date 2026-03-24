package com.school.ppmg.computer_equipment_store_system_api.dtos.service_request;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;

import java.time.LocalDateTime;

public record ServiceRequestResponse(
        Long id,
        Long serviceId,
        String serviceName,
        Long userId,
        String userEmail,
        ServiceRequestStatus status,
        String customerPhone,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}