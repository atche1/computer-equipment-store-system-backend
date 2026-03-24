package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.product_image.ProductImageRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.product_image.ProductImageResponse;
import com.school.ppmg.computer_equipment_store_system_api.models.Product;
import com.school.ppmg.computer_equipment_store_system_api.models.ProductImage;
import com.school.ppmg.computer_equipment_store_system_api.repositories.FileStorageService;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductImageRepository;
import com.school.ppmg.computer_equipment_store_system_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileStorageService fileStorageService;
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    @Value("${app.public-base-url}")
    private String publicBaseUrl;


    @Transactional
    public ProductImageResponse upload(Long productId, MultipartFile file, Boolean isMain) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId));

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is required");
        }

        // 1) запис на файл (примерно: uploads/)
        String url = fileStorageService.saveProductImage(productId, file); // връща публичен URL

        // 2) reuse на логиката ти за MAIN + запис в DB
        boolean makeMain = Boolean.TRUE.equals(isMain);
        if (!productImageRepository.existsByProductIdAndIsMainTrue(productId)) {
            makeMain = true;
        }

        ProductImage img = ProductImage.builder()
                .product(product)
                .imageUrl(url)
                .isMain(makeMain)
                .build();

        ProductImage saved = productImageRepository.save(img);

        if (makeMain) {
            unsetMainForOthers(productId, saved.getId());
        }

        return toResponse(saved);
    }
    private void unsetMainForOthers(Long productId, Long keepId) {
        var others = productImageRepository.findByProductIdAndIdNot(productId, keepId);
        for (var o : others) {
            if (Boolean.TRUE.equals(o.getIsMain())) o.setIsMain(false);
        }
        productImageRepository.saveAll(others);
    }

    @Transactional(readOnly = true)
    public List<ProductImageResponse> list(Long productId) {
        ensureProductExists(productId);
        return productImageRepository.findByProductIdOrderByIsMainDescIdAsc(productId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public ProductImageResponse add(Long productId, ProductImageRequest req) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId));

        boolean makeMain = Boolean.TRUE.equals(req.isMain());
        if (!productImageRepository.existsByProductIdAndIsMainTrue(productId)) {
            makeMain = true; // ако няма main – първата става main
        }

        ProductImage img = ProductImage.builder()
                .product(product)
                .imageUrl(req.imageUrl().trim())
                .isMain(makeMain)
                .build();

        ProductImage saved = productImageRepository.save(img);

        if (makeMain) {
            unsetMainForOthers(productId, saved.getId());
        }

        return toResponse(saved);
    }

    @Transactional
    public void delete(Long productId, Long imageId) {
        ProductImage img = productImageRepository.findByIdAndProductId(imageId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found: " + imageId));

        boolean wasMain = Boolean.TRUE.equals(img.getIsMain());
        String url = img.getImageUrl();

        productImageRepository.delete(img);

        // ✅ трий файла от диска (ако е локален)
        fileStorageService.deleteByPublicUrl(url);

        if (wasMain) {
            var left = productImageRepository.findByProductIdOrderByIsMainDescIdAsc(productId);
            if (!left.isEmpty()) {
                ProductImage first = left.get(0);
                first.setIsMain(true);
                productImageRepository.save(first);
                unsetMainForOthers(productId, first.getId());
            }
        }
    }

    @Transactional
    public void setMain(Long productId, Long imageId) {
        ProductImage img = productImageRepository.findByIdAndProductId(imageId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found: " + imageId));

        img.setIsMain(true);
        productImageRepository.save(img);
        unsetMainForOthers(productId, imageId);
    }



    private void ensureProductExists(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
        }
    }

    private ProductImageResponse toResponse(ProductImage img) {
        return new ProductImageResponse(
                img.getId(),
                resolveImageUrl(img.getImageUrl()),
                img.getIsMain()
        );
    }

    private String resolveImageUrl(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            return imageUrl;
        }

        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            return imageUrl;
        }

        if (imageUrl.startsWith("/")) {
            return publicBaseUrl + imageUrl;
        }

        return publicBaseUrl + "/" + imageUrl;
    }
}