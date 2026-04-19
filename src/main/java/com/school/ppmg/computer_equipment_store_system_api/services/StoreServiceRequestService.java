package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.CreateServiceRequestRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.ServiceRequestResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.service_request.UpdateServiceRequestStatusRequest;
import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StoreServiceRequestService {

    ServiceRequestResponse create(CreateServiceRequestRequest request);

    Page<ServiceRequestResponse> getMyRequests(Pageable pageable);

    ServiceRequestResponse getMyRequestById(Long id);

    Page<ServiceRequestResponse> getAll(ServiceRequestStatus status,
                                        String q,
                                        Long serviceId,
                                        LocalDate dateFrom,
                                        LocalDate dateTo,
                                        Pageable pageable);

    ServiceRequestResponse getById(Long id);

    ServiceRequestResponse updateStatus(Long id, UpdateServiceRequestStatusRequest request);
}