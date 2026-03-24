package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    Page<ServiceRequest> findByUserId(Long userId, Pageable pageable);

    Page<ServiceRequest> findByStatus(ServiceRequestStatus status, Pageable pageable);
}