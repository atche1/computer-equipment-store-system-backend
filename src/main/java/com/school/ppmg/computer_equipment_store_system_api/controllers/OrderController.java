package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.order.CheckoutRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.OrderResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.order.UpdateOrderStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public OrderResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return orderService.checkout(request);
    }

    @GetMapping("/my")
    public Page<OrderResponse> myOrders(Pageable pageable) {
        return orderService.getMyOrders(pageable);
    }

    @GetMapping("/my/{id}")
    public OrderResponse myOrderById(@PathVariable Long id) {
        return orderService.getMyOrderById(id);
    }

    @GetMapping
    public Page<OrderResponse> allOrders(Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id,
                                      @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateStatus(id, request);
    }
}