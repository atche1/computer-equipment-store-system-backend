package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.dtos.admin.AdminDashboardResponse;
import com.school.ppmg.computer_equipment_store_system_api.repositories.*;
import com.school.ppmg.computer_equipment_store_system_api.services.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final OrderRepository orderRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository storeServiceRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        var orders = orderRepository.findAll();
        var serviceRequests = serviceRequestRepository.findAll();

        Map<String, Long> ordersByStatus =
                orders.stream()
                        .collect(Collectors.groupingBy(o -> o.getStatus().name(), Collectors.counting()));

        Map<String, Long> serviceRequestsByStatus =
                serviceRequests.stream()
                        .collect(Collectors.groupingBy(s -> s.getStatus().name(), Collectors.counting()));

        return new AdminDashboardResponse(
                orders.size(),
                ordersByStatus,

                serviceRequests.size(),
                serviceRequestsByStatus,

                userRepository.count(),
                productRepository.count(),
                storeServiceRepository.count()
        );
    }
}