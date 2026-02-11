package com.school.ppmg.computer_equipment_store_system_api.repositories.specifications;

import com.school.ppmg.computer_equipment_store_system_api.models.Category;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {

    private CategorySpecification() {}

    public static Specification<Category> nameContains(String q) {
        return (root, query, cb) -> {
            if (q == null || q.trim().isBlank()) {
                return cb.conjunction();
            }
            String like = "%" + q.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), like);
        };
    }

    public static Specification<Category> isActiveEquals(Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("isActive"), isActive);
        };
    }
}