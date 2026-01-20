package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import com.school.ppmg.computer_equipment_store_system_api.models.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long> {
    List<CategoryAttribute> findByCategoryId(Long categoryId);

    @Query("""
        select ca.attribute from CategoryAttribute ca
        where ca.category.id = :categoryId
    """)
    List<Attribute> findAttributesForCategory(@Param("categoryId") Long categoryId);

    @Query("""
        select ca.attribute from CategoryAttribute ca
        where ca.category.id = :categoryId
          and ca.attribute.isFilterable = true
    """)
    List<Attribute> findFilterableAttributesForCategory(Long categoryId);
}
