package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.order.CheckoutRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.OrderResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);

    Page<OrderResponse> getMyOrders(Pageable pageable);

    OrderResponse getMyOrderById(Long orderId);

    Page<OrderResponse> getAllOrders(Pageable pageable);

    OrderResponse updateStatus(Long orderId, UpdateOrderStatusRequest request);
}