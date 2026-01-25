package com.school.ppmg.computer_equipment_store_system_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = {"product"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_images_product"))
    private Product product;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageUrl;

    @NotNull
    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;
}