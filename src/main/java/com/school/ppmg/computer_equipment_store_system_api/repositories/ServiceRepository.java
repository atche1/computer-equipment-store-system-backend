package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByIsActiveTrueOrderByNameAsc();

    Page<Service> findByIsActive(Boolean isActive, Pageable pageable);

    List<Service> findByIsActiveTrueOrderByCreatedAtDesc();

    @Query("""
        SELECT s
        FROM Service s
        WHERE s.isActive = true
          AND (
                :q IS NULL
                OR LOWER(s.name) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(s.description) LIKE LOWER(CONCAT('%', :q, '%'))
          )
          AND (:minPrice IS NULL OR s.price >= :minPrice)
          AND (:maxPrice IS NULL OR s.price <= :maxPrice)
    """)
    Page<Service> searchActive(@Param("q") String q,
                               @Param("minPrice") BigDecimal minPrice,
                               @Param("maxPrice") BigDecimal maxPrice,
                               Pageable pageable);
}