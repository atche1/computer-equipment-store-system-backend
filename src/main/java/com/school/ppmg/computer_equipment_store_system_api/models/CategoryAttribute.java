package com.school.ppmg.computer_equipment_store_system_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = {"category","attribute"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "category_attributes")
public class CategoryAttribute {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private CategoryAttributeId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_category_attributes_category"))
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_category_attributes_attribute"))
    private Attribute attribute;
}