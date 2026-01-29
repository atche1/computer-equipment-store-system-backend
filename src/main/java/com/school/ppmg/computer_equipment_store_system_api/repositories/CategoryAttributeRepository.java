package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import com.school.ppmg.computer_equipment_store_system_api.models.CategoryAttribute;
import com.school.ppmg.computer_equipment_store_system_api.models.CategoryAttributeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, CategoryAttributeId>{

    List<CategoryAttribute> findByCategory_Id(Long categoryId);
    boolean existsByCategory_IdAndAttribute_Id(Long categoryId, Long attributeId);
    void deleteByCategory_IdAndAttribute_Id(Long categoryId, Long attributeId);

}
