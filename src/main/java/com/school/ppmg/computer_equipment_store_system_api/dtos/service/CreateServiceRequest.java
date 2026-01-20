package com.school.ppmg.computer_equipment_store_system_api.dtos.service;

import jakarta.validation.constraints.*;

public record CreateServiceRequest(
        @NotNull Long serviceId,
        @NotBlank @Size(max = 20) String customerPhone,
        @NotBlank @Size(max = 5000) String description
) {}
