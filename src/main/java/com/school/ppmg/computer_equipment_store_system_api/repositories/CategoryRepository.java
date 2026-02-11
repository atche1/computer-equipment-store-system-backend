package com.school.ppmg.computer_equipment_store_system_api.repositories;

import com.school.ppmg.computer_equipment_store_system_api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByIsActiveTrueOrderByNameAsc();

    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, Long id);
}
