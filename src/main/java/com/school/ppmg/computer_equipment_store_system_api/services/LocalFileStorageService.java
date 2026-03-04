package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.repositories.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    @Override
    public String saveProductImage(Long productId, MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only image files are allowed");
        }

        String original = file.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(original);
        ext = (ext == null || ext.isBlank()) ? guessExt(contentType) : ext.toLowerCase();

        // {uploadDir}/products/{productId}/
        Path root = Path.of(uploadDir).toAbsolutePath().normalize();
        Path dir = root.resolve("products").resolve(String.valueOf(productId));

        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create upload directory");
        }

        String filename = UUID.randomUUID() + "." + ext;
        Path target = dir.resolve(filename).normalize();

        // safety: не позволяваме излизане извън root
        if (!target.startsWith(dir)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
        }

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not save file");
        }

        // публичен URL (serves from /uploads/**)
        return "/uploads/products/" + productId + "/" + filename;
    }

    @Override
    public void deleteByPublicUrl(String imageUrl) {
        // трие само локални файлове
        if (imageUrl == null || imageUrl.isBlank()) return;
        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) return;
        if (!imageUrl.startsWith("/uploads/")) return;

        Path root = Path.of(uploadDir).toAbsolutePath().normalize();

        // imageUrl: /uploads/.... => remove "/uploads/"
        String relative = imageUrl.substring("/uploads/".length());
        Path target = root.resolve(relative).normalize();

        // safety
        if (!target.startsWith(root)) return;

        try {
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
            // може да логнеш warning, но не е критично
        }
    }

    private String guessExt(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case "image/gif" -> "gif";
            default -> "bin";
        };
    }
}