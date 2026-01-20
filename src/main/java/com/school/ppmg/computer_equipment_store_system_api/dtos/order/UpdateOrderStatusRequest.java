package com.school.ppmg.computer_equipment_store_system_api.dtos.order;

import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull OrderStatus status
) {}