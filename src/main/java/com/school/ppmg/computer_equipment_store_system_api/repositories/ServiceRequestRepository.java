package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    Page<ServiceRequest> findByStatus(ServiceRequestStatus status, Pageable pageable);

    Page<ServiceRequest> findByUserId(Long userId, Pageable pageable);

    Page<ServiceRequest> findByServiceId(Long serviceId, Pageable pageable);

    Page<ServiceRequest> findByCreatedAtBetween(
            LocalDateTime from, LocalDateTime to, Pageable pageable);
}
