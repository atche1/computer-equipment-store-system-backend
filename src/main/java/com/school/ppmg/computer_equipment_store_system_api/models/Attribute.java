package com.school.ppmg.computer_equipment_store_system_api.models;

import com.school.ppmg.computer_equipment_store_system_api.enums.AttributeDataType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 20)
    private AttributeDataType dataType;

    @Size(max = 30)
    @Column(length = 30)
    private String unit;

    @NotNull
    @Column(name = "is_filterable", nullable = false)
    private Boolean isFilterable = false;
}