package com.school.ppmg.computer_equipment_store_system_api.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CategoryAttributeId implements Serializable {
    private Long categoryId;
    private Long attributeId;
}