package com.school.ppmg.computer_equipment_store_system_api.repositories;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveProductImage(Long productId, MultipartFile file);
}