package com.school.ppmg.computer_equipment_store_system_api.repositories.specifications;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import org.springframework.data.jpa.domain.Specification;

public class AttributeSpecification {

    private AttributeSpecification() { }

    public static Specification<Attribute> nameContains(String q) {
        return (root, query, cb) -> {
            if (q == null || q.trim().isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + q.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<Attribute> hasDataType(AttributeDataType dataType) {
        return (root, query, cb) ->
                dataType == null ? cb.conjunction() : cb.equal(root.get("dataType"), dataType);
    }

    public static Specification<Attribute> isFilterable(Boolean filterable) {
        return (root, query, cb) ->
                filterable == null ? cb.conjunction() : cb.equal(root.get("isFilterable"), filterable);
    }

    public static Specification<Attribute> unitEquals(String unit) {
        return (root, query, cb) -> {
            if (unit == null || unit.trim().isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("unit")), unit.trim().toLowerCase());
        };
    }
}