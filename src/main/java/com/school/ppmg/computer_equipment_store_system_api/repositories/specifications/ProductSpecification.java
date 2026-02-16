package com.school.ppmg.computer_equipment_store_system_api.repositories.specifications;

import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<Product> nameOrDescriptionContains(String q) {
        return (root, query, cb) -> {
            if (q == null || q.trim().isBlank()) return cb.conjunction();
            String like = "%" + q.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }

    public static Specification<Product> categoryIdEquals(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return cb.conjunction();
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> isActiveEquals(Boolean isActive) {
        return (root, query, cb) -> isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }

    public static Specification<Product> priceGte(BigDecimal minPrice) {
        return (root, query, cb) -> minPrice == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLte(BigDecimal maxPrice) {
        return (root, query, cb) -> maxPrice == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> inStock(Boolean inStock) {
        return (root, query, cb) -> {
            if (inStock == null) return cb.conjunction();
            return inStock ? cb.greaterThan(root.get("quantity"), 0) : cb.equal(root.get("quantity"), 0);
        };
    }
}