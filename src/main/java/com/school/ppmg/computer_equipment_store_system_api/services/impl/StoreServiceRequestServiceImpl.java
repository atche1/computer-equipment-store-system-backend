package com.school.ppmg.computer_equipment_store_system_api.services.impl;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.CreateServiceRequestRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.ServiceRequestResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.UpdateServiceRequestStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.Service;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import com.school.ppmg.computer_equipment_store_system_api.models.User;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ServiceRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ServiceRequestRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.UserRepository;
import com.school.ppmg.computer_equipment_store_system_api.services.EmailService;
import com.school.ppmg.computer_equipment_store_system_api.services.StoreServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StoreServiceRequestServiceImpl implements StoreServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public ServiceRequestResponse create(CreateServiceRequestRequest request) {
        User user = getCurrentUser();

        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        if (!Boolean.TRUE.equals(service.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service is not active");
        }

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setUser(user);
        serviceRequest.setService(service);
        serviceRequest.setStatus(ServiceRequestStatus.NEW);
        serviceRequest.setCustomerPhone(request.customerPhone());
        serviceRequest.setDescription(request.description());

        ServiceRequest saved = serviceRequestRepository.save(serviceRequest);

        try {
            emailService.sendServiceRequestCreatedEmail(saved);
        } catch (Exception ignored) {
            // не чупим create-а, ако имейлът не се изпрати
        }

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getMyRequests(Pageable pageable) {
        User user = getCurrentUser();
        return serviceRequestRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceRequestResponse getMyRequestById(Long id) {
        User user = getCurrentUser();

        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service request not found"));

        if (!serviceRequest.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This service request does not belong to you");
        }

        return toResponse(serviceRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getAll(ServiceRequestStatus status,
                                               String q,
                                               Long serviceId,
                                               LocalDate dateFrom,
                                               LocalDate dateTo,
                                               Pageable pageable) {

        LocalDateTime createdFrom = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime createdTo = dateTo != null ? dateTo.plusDays(1).atStartOfDay() : null;

        return serviceRequestRepository.searchAdmin(
                status,
                q,
                serviceId,
                createdFrom,
                createdTo,
                pageable
        ).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceRequestResponse getById(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service request not found"));

        return toResponse(serviceRequest);
    }

    @Override
    @Transactional
    public ServiceRequestResponse updateStatus(Long id, UpdateServiceRequestStatusRequest request) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service request not found"));

        ServiceRequestStatus oldStatus = serviceRequest.getStatus();
        ServiceRequestStatus newStatus = request.status();

        if (oldStatus == newStatus) {
            return toResponse(serviceRequest);
        }

        serviceRequest.setStatus(newStatus);
        ServiceRequest saved = serviceRequestRepository.save(serviceRequest);

        try {
            emailService.sendServiceRequestStatusChangedEmail(saved, oldStatus, newStatus);
        } catch (Exception ignored) {
        }

        return toResponse(saved);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    private ServiceRequestResponse toResponse(ServiceRequest serviceRequest) {
        return new ServiceRequestResponse(
                serviceRequest.getId(),
                serviceRequest.getService().getId(),
                serviceRequest.getService().getName(),
                serviceRequest.getUser().getId(),
                serviceRequest.getUser().getEmail(),
                serviceRequest.getStatus(),
                serviceRequest.getCustomerPhone(),
                serviceRequest.getDescription(),
                serviceRequest.getCreatedAt(),
                serviceRequest.getUpdatedAt()
        );
    }
}