package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.ProductAttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {
    List<ProductAttributeValue> findByProductId(Long productId);
    Optional<ProductAttributeValue> findByProductIdAndAttributeId(Long productId, Long attributeId);

    @Query("""
        select distinct pav.product from ProductAttributeValue pav
        where pav.attribute.id = :attributeId
          and lower(pav.valueText) = lower(:value)
          and pav.product.isActive = true
    """)
    Page<Product> filterByTextAttribute(
            @Param("attributeId") Long attributeId,
            @Param("value") String value,
            Pageable pageable);

    @Query("""
        select distinct pav.product from ProductAttributeValue pav
        where pav.attribute.id = :attributeId
          and pav.valueNumber between :min and :max
          and pav.product.isActive = true
    """)
    Page<Product> filterByNumberAttributeRange(
            @Param("attributeId") Long attributeId,
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max,
            Pageable pageable);
}
