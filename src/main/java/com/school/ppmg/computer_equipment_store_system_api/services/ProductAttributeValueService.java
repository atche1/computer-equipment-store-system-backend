package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value.ProductAttributeValueRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.product_attribute_value.ProductAttributeValueResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Attribute;
import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.ProductAttributeValue;
import com.school.ppmg.computer_equipment_store_system_api.repositories.AttributeRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductAttributeValueRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ProductAttributeValueService {

    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;
    private final ProductAttributeValueRepository pavRepository;

    public ProductAttributeValueService(
            ProductRepository productRepository,
            AttributeRepository attributeRepository,
            ProductAttributeValueRepository pavRepository
    ) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
        this.pavRepository = pavRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductAttributeValueResponse> listByProduct(Long productId) {
        ensureProductExists(productId);

        return pavRepository.findByProductId(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Upsert (update or create) values by (productId + attributeId).
     * Ако valueText/valueNumber/valueBoolean са всички null/blank -> третираме като "изтрий стойността".
     */
    public List<ProductAttributeValueResponse> upsertBatch(Long productId, List<ProductAttributeValueRequest> requests) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId));

        for (ProductAttributeValueRequest req : requests) {
            Attribute attribute = attributeRepository.findById(req.attributeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found: " + req.attributeId()));

            boolean empty =
                    (req.valueText() == null || req.valueText().isBlank())
                            && req.valueNumber() == null
                            && req.valueBoolean() == null;

            var existingOpt = pavRepository.findByProductIdAndAttributeId(productId, req.attributeId());

            if (empty) {
                existingOpt.ifPresent(pavRepository::delete);
                continue;
            }

            ProductAttributeValue pav = existingOpt.orElseGet(() -> ProductAttributeValue.builder()
                    .product(product)
                    .attribute(attribute)
                    .build());

            pav.setValueText(req.valueText());
            pav.setValueNumber(req.valueNumber());
            pav.setValueBoolean(req.valueBoolean());

            pavRepository.save(pav);
        }

        return listByProduct(productId);
    }

    public void deleteOne(Long productId, Long pavId) {
        ensureProductExists(productId);

        ProductAttributeValue pav = pavRepository.findById(pavId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductAttributeValue not found: " + pavId));

        if (!pav.getProduct().getId().equals(productId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value does not belong to product " + productId);
        }

        pavRepository.delete(pav);
    }

    private void ensureProductExists(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
        }
    }

    private ProductAttributeValueResponse toResponse(ProductAttributeValue pav) {
        Attribute a = pav.getAttribute();
        return new ProductAttributeValueResponse(
                pav.getId(),
                pav.getProduct().getId(),
                a.getId(),
                a.getName(),
                a.getDataType(),
                a.getUnit(),
                a.getIsFilterable(),
                pav.getValueText(),
                pav.getValueNumber(),
                pav.getValueBoolean()
        );
    }
}