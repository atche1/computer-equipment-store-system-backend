package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.product.ProductRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.product.ProductResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Category;
import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CategoryRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse create(ProductRequest req) {
        String name = req.name().trim();
        String description = normalizeNullable(req.description());
        boolean isActive = (req.isActive() == null) ? true : req.isActive();

        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found: " + req.categoryId()
                ));

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(req.price())
                .quantity(req.quantity())
                .isActive(isActive)
                .category(category)
                .build();

        return toResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
        return toResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(
            String q,
            Long categoryId,
            Boolean isActive,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            java.util.List<String> attrText,
            java.util.List<String> attrNumMin,
            java.util.List<String> attrNumMax,
            java.util.List<String> attrBool,
            Pageable pageable
    ) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minPrice cannot be greater than maxPrice");
        }

        Specification<Product> spec = Specification
                .where(ProductSpecification.nameOrDescriptionContains(q))
                .and(ProductSpecification.categoryIdEquals(categoryId))
                .and(ProductSpecification.isActiveEquals(isActive))
                .and(ProductSpecification.priceGte(minPrice))
                .and(ProductSpecification.priceLte(maxPrice))
                .and(ProductSpecification.inStock(inStock));

        // TEXT: ["12:Logitech", "13:Black"]
        if (attrText != null) {
            for (String token : attrText) {
                IdText p = parseIdText(token, "attrText");
                spec = spec.and(ProductSpecification.attrTextContains(p.id(), p.text()));
            }
        }

        // NUMBER MIN: ["5:8", "6:2.5"]
        if (attrNumMin != null) {
            for (String token : attrNumMin) {
                IdNum p = parseIdNum(token, "attrNumMin");
                spec = spec.and(ProductSpecification.attrNumberGte(p.id(), p.num()));
            }
        }

        // NUMBER MAX: ["5:32", "6:10"]
        if (attrNumMax != null) {
            for (String token : attrNumMax) {
                IdNum p = parseIdNum(token, "attrNumMax");
                spec = spec.and(ProductSpecification.attrNumberLte(p.id(), p.num()));
            }
        }

        // BOOL: ["9:true", "10:false"]
        if (attrBool != null) {
            for (String token : attrBool) {
                IdBool p = parseIdBool(token, "attrBool");
                spec = spec.and(ProductSpecification.attrBoolEquals(p.id(), p.value()));
            }
        }

        return productRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private record IdText(Long id, String text) {}
    private record IdNum(Long id, BigDecimal num) {}
    private record IdBool(Long id, Boolean value) {}

    private IdText parseIdText(String token, String paramName) {
        if (token == null || token.isBlank() || !token.contains(":")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
        String[] parts = token.split(":", 2);
        try {
            Long id = Long.parseLong(parts[0].trim());
            String text = parts[1] == null ? null : parts[1].trim();
            return new IdText(id, text);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
    }

    private IdNum parseIdNum(String token, String paramName) {
        if (token == null || token.isBlank() || !token.contains(":")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
        String[] parts = token.split(":", 2);
        try {
            Long id = Long.parseLong(parts[0].trim());
            BigDecimal num = new BigDecimal(parts[1].trim());
            return new IdNum(id, num);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
    }

    private IdBool parseIdBool(String token, String paramName) {
        if (token == null || token.isBlank() || !token.contains(":")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
        String[] parts = token.split(":", 2);
        try {
            Long id = Long.parseLong(parts[0].trim());
            String v = parts[1].trim().toLowerCase();
            if (!v.equals("true") && !v.equals("false")) {
                throw new IllegalArgumentException();
            }
            return new IdBool(id, Boolean.valueOf(v));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + paramName + " token: " + token);
        }
    }

    public ProductResponse update(Long id, ProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));

        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found: " + req.categoryId()
                ));

        product.setName(req.name().trim());
        product.setDescription(normalizeNullable(req.description()));
        product.setPrice(req.price());
        product.setQuantity(req.quantity());
        product.setIsActive(req.isActive() == null ? product.getIsActive() : req.isActive());
        product.setCategory(category);

        return toResponse(productRepository.save(product));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
        }
        // soft delete през @SQLDelete
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getQuantity(),
                p.getIsActive(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }

    private String normalizeNullable(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isBlank() ? null : t;
    }
}