package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeResponse;
import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import com.school.ppmg.computer_equipment_store_system_api.repositories.AttributeRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.specifications.AttributeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public AttributeResponse create(AttributeRequest req) {
        Attribute attribute = Attribute.builder()
                .name(req.name().trim())
                .dataType(req.dataType())
                .unit(normalizeNullable(req.unit()))
                .isFilterable(req.isFilterable() != null ? req.isFilterable() : false)
                .build();

        Attribute saved = attributeRepository.save(attribute);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AttributeResponse getById(Long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found: " + id));
        return toResponse(attribute);
    }

    @Transactional(readOnly = true)
    public Page<AttributeResponse> search(
            String q,
            AttributeDataType dataType,
            Boolean filterable,
            String unit,
            Pageable pageable
    ) {
        Specification<Attribute> spec =
                AttributeSpecification.nameContains(q)
                        .and(AttributeSpecification.hasDataType(dataType))
                        .and(AttributeSpecification.isFilterable(filterable))
                        .and(AttributeSpecification.unitEquals(unit));

        return attributeRepository.findAll(spec, pageable).map(this::toResponse);
    }

    public AttributeResponse update(Long id, AttributeRequest req) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found: " + id));

        attribute.setName(req.name().trim());
        attribute.setDataType(req.dataType());
        attribute.setUnit(normalizeNullable(req.unit()));

        if (req.isFilterable() != null) {
            attribute.setIsFilterable(req.isFilterable());
        }
        // ако isFilterable е null -> не го пипаме (запазваме старото)

        Attribute saved = attributeRepository.save(attribute);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!attributeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found: " + id);
        }
        // Soft delete чрез @SQLDelete (update deleted_at=NOW())
        attributeRepository.deleteById(id);
    }

    private AttributeResponse toResponse(Attribute a) {
        return new AttributeResponse(
                a.getId(),
                a.getName(),
                a.getDataType(),
                a.getUnit(),
                a.getIsFilterable(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }

    private String normalizeNullable(String s) {
        if (s == null) return null;
        String trimmed = s.trim();
        return trimmed.isBlank() ? null : trimmed;
    }
}