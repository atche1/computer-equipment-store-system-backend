package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByIsActiveTrueOrderByNameAsc();

    Page<Service> findByIsActive(Boolean isActive, Pageable pageable);
}