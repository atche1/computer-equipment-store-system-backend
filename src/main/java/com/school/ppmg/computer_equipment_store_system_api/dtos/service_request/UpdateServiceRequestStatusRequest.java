package com.school.ppmg.computer_equipment_store_system_api.dtos.service_request;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateServiceRequestStatusRequest(@NotNull(message = "Status is required")
                                                ServiceRequestStatus status) {
}
