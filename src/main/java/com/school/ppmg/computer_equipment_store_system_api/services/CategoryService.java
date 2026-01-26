package com.school.ppmg.computer_equipment_store_system_api.services;
import com.school.ppmg.computer_equipment_store_system_api.dtos.category.CategoryRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.category.CategoryResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Category;
import com.school.ppmg.computer_equipment_store_system_api.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse create(CategoryRequest req) {
        String name = req.name().trim();
        String slug = normalizeSlug(req.slug(), name);
        boolean isActive = (req.isActive() == null) ? true : req.isActive();

        if (categoryRepository.existsBySlug(slug)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category slug already exists: " + slug);
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .isActive(isActive)
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
        return toResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> search(String q, Pageable pageable) {
        if (q == null || q.trim().isBlank()) {
            return categoryRepository.findAll(pageable).map(this::toResponse);
        }
        return categoryRepository.findByNameContainingIgnoreCase(q.trim(), pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> listActive() {
        return categoryRepository.findByIsActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse update(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));

        String name = req.name().trim();
        String slug = normalizeSlug(req.slug(), name);
        boolean isActive = (req.isActive() == null) ? category.getIsActive() : req.isActive();

        if (categoryRepository.existsBySlugAndIdNot(slug, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category slug already exists: " + slug);
        }

        category.setName(name);
        category.setSlug(slug);
        category.setIsActive(isActive);

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(
                c.getId(),
                c.getName(),
                c.getSlug(),
                c.getIsActive(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
    private String normalizeSlug(String slugFromReq, String nameFallback) {
        String base = (slugFromReq == null || slugFromReq.isBlank())
                ? nameFallback
                : slugFromReq;

        String transliterated = transliterateToLatin(base);

        String slug = transliterated
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");

        if (slug.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid slug/name (cannot generate slug)"
            );
        }

        return slug;
    }

    private String transliterateToLatin(String text) {
        return text
                .replace("а", "a").replace("б", "b").replace("в", "v")
                .replace("г", "g").replace("д", "d").replace("е", "e")
                .replace("ж", "zh").replace("з", "z").replace("и", "i")
                .replace("й", "y").replace("к", "k").replace("л", "l")
                .replace("м", "m").replace("н", "n").replace("о", "o")
                .replace("п", "p").replace("р", "r").replace("с", "s")
                .replace("т", "t").replace("у", "u").replace("ф", "f")
                .replace("х", "h").replace("ц", "ts").replace("ч", "ch")
                .replace("ш", "sh").replace("щ", "sht").replace("ъ", "a")
                .replace("ь", "y").replace("ю", "yu").replace("я", "ya")
                .replace("А", "a").replace("Б", "b").replace("В", "v")
                .replace("Г", "g").replace("Д", "d").replace("Е", "e")
                .replace("Ж", "zh").replace("З", "z").replace("И", "i")
                .replace("Й", "y").replace("К", "k").replace("Л", "l")
                .replace("М", "m").replace("Н", "n").replace("О", "o")
                .replace("П", "p").replace("Р", "r").replace("С", "s")
                .replace("Т", "t").replace("У", "u").replace("Ф", "f")
                .replace("Х", "h").replace("Ц", "ts").replace("Ч", "ch")
                .replace("Ш", "sh").replace("Щ", "sht").replace("Ъ", "a")
                .replace("Ь", "y").replace("Ю", "yu").replace("Я", "ya");
    }

}