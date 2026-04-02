package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service.ServiceResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Service;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ServiceRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.StoreServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreServiceServiceImpl implements StoreServiceService {

    private final ServiceRepository serviceRepository;


    @Override
    @Transactional
    public ServiceResponse create(ServiceRequest request) {
        Service service = new Service();
        service.setName(request.name());
        service.setDescription(request.description());
        service.setPrice(request.price());
        service.setIsActive(request.isActive());

        return toResponse(serviceRepository.save(service));
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceResponse> getAllActive(String q, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        String keyword = (q != null && !q.isBlank()) ? q.trim() : null;
        return serviceRepository.searchActive(keyword, minPrice, maxPrice, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse getById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        return toResponse(service);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponse> getAllActive() {
        return serviceRepository.findByIsActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ServiceResponse> getAllForAdmin(Boolean isActive, Pageable pageable) {
        if (isActive == null) {
            return serviceRepository.findAll(pageable).map(this::toResponse);
        }

        return serviceRepository.findByIsActive(isActive, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public ServiceResponse update(Long id, ServiceRequest request) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        service.setName(request.name());
        service.setDescription(request.description());
        service.setPrice(request.price());
        service.setIsActive(request.isActive());

        return toResponse(serviceRepository.save(service));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        serviceRepository.delete(service);
    }

    private ServiceResponse toResponse(Service service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getIsActive(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}