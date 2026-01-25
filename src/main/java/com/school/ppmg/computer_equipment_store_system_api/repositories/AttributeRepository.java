package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findByNameContainingIgnoreCase(String q);

    List<Attribute> findByDataType(AttributeDataType dataType);

    List<Attribute> findByIsFilterableTrue();

    List<Attribute> findByIsFilterableTrueAndDataType(AttributeDataType dataType);
}
