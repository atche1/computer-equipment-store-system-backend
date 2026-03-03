package com.school.ppmg.computer_equipment_store_system_api.controllers;

import com.school.ppmg.computer_equipment_store_system_api.dtos.product_image.ProductImageRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.product_image.ProductImageResponse;
import com.school.ppmg.computer_equipment_store_system_api.services.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/images")
public class ProductImageController {

    private final ProductImageService productImageService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductImageResponse upload(@PathVariable Long productId,
                                       @RequestPart("file") MultipartFile file,
                                       @RequestParam(value = "isMain", required = false, defaultValue = "false") boolean isMain) {
        return productImageService.upload(productId, file, isMain);
    }

    @GetMapping
    public List<ProductImageResponse> list(@PathVariable Long productId) {
        return productImageService.list(productId);
    }

    @PostMapping
    public ProductImageResponse add(@PathVariable Long productId, @Valid @RequestBody ProductImageRequest req) {
        return productImageService.add(productId, req);
    }

    @DeleteMapping("/{imageId}")
    public void delete(@PathVariable Long productId, @PathVariable Long imageId) {
        productImageService.delete(productId, imageId);
    }

    @PutMapping("/{imageId}/main")
    public void setMain(@PathVariable Long productId, @PathVariable Long imageId) {
        productImageService.setMain(productId, imageId);
    }
}