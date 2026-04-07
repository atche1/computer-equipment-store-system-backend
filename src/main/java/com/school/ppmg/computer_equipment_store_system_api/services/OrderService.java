package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.order.CheckoutRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.OrderResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.UpdateOrderStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);

    Page<OrderResponse> getMyOrders(Pageable pageable);

    OrderResponse getMyOrderById(Long orderId);
    OrderResponse getOrderById(Long orderId);

    OrderResponse updateStatus(Long orderId, UpdateOrderStatusRequest request);
    Page<OrderResponse> searchAdminOrders(
            OrderStatus status,
            String orderNumber,
            String customerName,
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            Pageable pageable
    );
}