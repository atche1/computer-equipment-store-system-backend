package com.school.ppmg.computer_equipment_store_system_api.dtos.product_image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductImageRequest(
        @NotBlank
        @Size(max = 1000)
        @Pattern(
                regexp = "^(https?://).+",
                message = "imageUrl must start with http:// or https://"
        )
        String imageUrl,

        @NotNull
        Boolean isMain
) {}