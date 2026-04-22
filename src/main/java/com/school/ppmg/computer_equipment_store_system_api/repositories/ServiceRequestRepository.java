package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.enums.ServiceRequestStatus;
import com.school.ppmg.computer_equipment_store_system_api.models.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    Page<ServiceRequest> findByUserId(Long userId, Pageable pageable);

    Page<ServiceRequest> findByStatus(ServiceRequestStatus status, Pageable pageable);

    @Query("""
            SELECT sr
            FROM ServiceRequest sr
            JOIN sr.service s
            JOIN sr.user u
            WHERE (:status IS NULL OR sr.status = :status)
              AND (:serviceId IS NULL OR s.id = :serviceId)
              AND (
                    :q IS NULL OR :q = '' OR
                    LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
                    LOWER(u.email) LIKE LOWER(CONCAT('%', :q, '%')) OR
                    LOWER(sr.customerPhone) LIKE LOWER(CONCAT('%', :q, '%')) OR
                    CAST(sr.id AS string) LIKE CONCAT('%', :q, '%')
                  )
              AND (:createdFrom IS NULL OR sr.createdAt >= :createdFrom)
              AND (:createdTo IS NULL OR sr.createdAt < :createdTo)
            """)
    Page<ServiceRequest> searchAdmin(
            @Param("status") ServiceRequestStatus status,
            @Param("q") String q,
            @Param("serviceId") Long serviceId,
            @Param("createdFrom") LocalDateTime createdFrom,
            @Param("createdTo") LocalDateTime createdTo,
            Pageable pageable
    );
}