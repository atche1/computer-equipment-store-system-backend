package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderByIsMainDescIdAsc(Long productId);

    Optional<ProductImage> findByIdAndProductId(Long id, Long productId);

    boolean existsByProductIdAndIsMainTrue(Long productId);

    List<ProductImage> findByProductIdAndIdNot(Long productId, Long id);
}
