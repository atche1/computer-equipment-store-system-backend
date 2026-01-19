package com.school.ppmg.computer_equipment_store_system_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = {"product","attribute"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_attribute_values")
public class ProductAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pav_product"))
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pav_attribute"))
    private Attribute attribute;

    @Size(max = 500)
    @Column(name = "value_text", length = 500)
    private String valueText;

    @Digits(integer = 12, fraction = 3)
    @Column(name = "value_number", precision = 15, scale = 3)
    private BigDecimal valueNumber;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @AssertTrue(message = "There must be at least one value.(text/number/boolean)")
    public boolean isAnyValuePresent() {
        return (valueText != null && !valueText.isBlank())
                || valueNumber != null
                || valueBoolean != null;
    }
}