package com.school.ppmg.computer_equipment_store_system_api.repositories.specifications;

import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.ProductAttributeValue;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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

    // =========================
    // FILTERS BY ATTRIBUTES
    // =========================

    public static Specification<Product> attrTextContains(Long attributeId, String value) {
        return (root, query, cb) -> {
            if (attributeId == null || value == null || value.trim().isBlank()) return cb.conjunction();

            if (!Long.class.equals(query.getResultType())) {
                query.distinct(true);
            }

            String like = "%" + value.trim().toLowerCase() + "%";

            Subquery<Integer> sq = query.subquery(Integer.class);
            Root<ProductAttributeValue> pav = sq.from(ProductAttributeValue.class);

            sq.select(cb.literal(1))
                    .where(
                            cb.equal(pav.get("product"), root),
                            cb.equal(pav.get("attribute").get("id"), attributeId),
                            cb.isNotNull(pav.get("valueText")),
                            cb.like(cb.lower(pav.get("valueText")), like)
                    );

            return cb.exists(sq);
        };
    }

    public static Specification<Product> attrNumberGte(Long attributeId, BigDecimal min) {
        return (root, query, cb) -> {
            if (attributeId == null || min == null) return cb.conjunction();

            if (!Long.class.equals(query.getResultType())) {
                query.distinct(true);
            }

            Subquery<Integer> sq = query.subquery(Integer.class);
            Root<ProductAttributeValue> pav = sq.from(ProductAttributeValue.class);

            sq.select(cb.literal(1))
                    .where(
                            cb.equal(pav.get("product"), root),
                            cb.equal(pav.get("attribute").get("id"), attributeId),
                            cb.isNotNull(pav.get("valueNumber")),
                            cb.greaterThanOrEqualTo(pav.get("valueNumber"), min)
                    );

            return cb.exists(sq);
        };
    }

    public static Specification<Product> attrNumberLte(Long attributeId, BigDecimal max) {
        return (root, query, cb) -> {
            if (attributeId == null || max == null) return cb.conjunction();

            if (!Long.class.equals(query.getResultType())) {
                query.distinct(true);
            }

            Subquery<Integer> sq = query.subquery(Integer.class);
            Root<ProductAttributeValue> pav = sq.from(ProductAttributeValue.class);

            sq.select(cb.literal(1))
                    .where(
                            cb.equal(pav.get("product"), root),
                            cb.equal(pav.get("attribute").get("id"), attributeId),
                            cb.isNotNull(pav.get("valueNumber")),
                            cb.lessThanOrEqualTo(pav.get("valueNumber"), max)
                    );

            return cb.exists(sq);
        };
    }

    public static Specification<Product> attrBoolEquals(Long attributeId, Boolean value) {
        return (root, query, cb) -> {
            if (attributeId == null || value == null) return cb.conjunction();

            if (!Long.class.equals(query.getResultType())) {
                query.distinct(true);
            }

            Subquery<Integer> sq = query.subquery(Integer.class);
            Root<ProductAttributeValue> pav = sq.from(ProductAttributeValue.class);

            sq.select(cb.literal(1))
                    .where(
                            cb.equal(pav.get("product"), root),
                            cb.equal(pav.get("attribute").get("id"), attributeId),
                            cb.isNotNull(pav.get("valueBoolean")),
                            cb.equal(pav.get("valueBoolean"), value)
                    );

            return cb.exists(sq);
        };
    }
}