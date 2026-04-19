package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface StoreServiceService {

    ServiceResponse create(ServiceRequest request);

    ServiceResponse getById(Long id);

    List<ServiceResponse> getAllActive();

    Page<ServiceResponse> getAllActive(String q, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<ServiceResponse> getAllForAdmin(String q,
                                         Boolean isActive,
                                         BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         Pageable pageable);

    ServiceResponse update(Long id, ServiceRequest request);

    void delete(Long id);
}