package com.school.ppmg.computer_equipment_store_system_api.dtos.service;


import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateServiceRequestStatusRequest(
        @NotNull ServiceRequestStatus status
) {}
