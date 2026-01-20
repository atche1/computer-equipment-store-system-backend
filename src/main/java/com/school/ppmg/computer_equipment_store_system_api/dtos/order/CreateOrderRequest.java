package com.school.ppmg.computer_equipment_store_system_api.dtos.order;

import jakarta.validation.constraints.*;

public record CreateOrderRequest(
        @NotBlank @Size(max = 120) String deliveryName,
        @NotBlank @Size(max = 20) String deliveryPhone,
        @NotBlank @Size(max = 255) String deliveryAddress
) {}