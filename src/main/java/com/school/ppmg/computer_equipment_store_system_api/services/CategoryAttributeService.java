package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.attribute.AttributeResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import com.school.ppmg.computer_equipment_store_system_api.models.Category;
import com.school.ppmg.computer_equipment_store_system_api.models.CategoryAttribute;
import com.school.ppmg.computer_equipment_store_system_api.models.CategoryAttributeId;
import com.school.ppmg.computer_equipment_store_system_api.repositories.AttributeRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CategoryAttributeRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CategoryAttributeService {

    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    public CategoryAttributeService(
            CategoryRepository categoryRepository,
            AttributeRepository attributeRepository,
            CategoryAttributeRepository categoryAttributeRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
        this.categoryAttributeRepository = categoryAttributeRepository;
    }

    @Transactional(readOnly = true)
    public List<AttributeResponse> listAttributes(Long categoryId) {
        ensureCategoryExists(categoryId);

        return categoryAttributeRepository.findByCategory_Id(categoryId)
                .stream()
                .map(CategoryAttribute::getAttribute)
                .filter(a -> a != null) // safety при soft-delete на Attribute
                .map(this::toAttributeResponse)
                .toList();
    }

    public AttributeResponse addAttribute(Long categoryId, Long attributeId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));

        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found: " + attributeId));

        if (categoryAttributeRepository.existsByCategory_IdAndAttribute_Id(categoryId, attributeId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Attribute " + attributeId + " already assigned to category " + categoryId
            );
        }

        CategoryAttributeId id = new CategoryAttributeId(categoryId, attributeId);

        CategoryAttribute ca = CategoryAttribute.builder()
                .id(id)
                .category(category)
                .attribute(attribute)
                .build();

        categoryAttributeRepository.save(ca);

        return toAttributeResponse(attribute);
    }

    public void removeAttribute(Long categoryId, Long attributeId) {
        ensureCategoryExists(categoryId);

        if (!categoryAttributeRepository.existsByCategory_IdAndAttribute_Id(categoryId, attributeId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CategoryAttribute not found: categoryId=" + categoryId + ", attributeId=" + attributeId
            );
        }

        categoryAttributeRepository.deleteByCategory_IdAndAttribute_Id(categoryId, attributeId);
    }

    private void ensureCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId);
        }
    }

    private AttributeResponse toAttributeResponse(Attribute a) {
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
}