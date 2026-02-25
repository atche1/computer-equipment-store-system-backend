package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
    Page<Product> findByIsActiveTrue(Pageable pageable);

    Page<Product> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);

    Page<Product> findByIsActiveTrueAndPriceBetween(
            BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Product> findByIsActiveTrueAndQuantityGreaterThan(
            Integer quantity, Pageable pageable); // quantity = 0

    Page<Product> findByIsActiveTrueAndNameContainingIgnoreCase(
            String keyword, Pageable pageable);

    @Query("""
        select p from Product p
        where p.isActive = true
          and (
            lower(p.name) like lower(concat('%', :q, '%'))
            or lower(p.description) like lower(concat('%', :q, '%'))
          )
    """)
    Page<Product> searchActive(@Param("q") String q, Pageable pageable);

    Page<Product> findByIsActive(Boolean isActive, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
