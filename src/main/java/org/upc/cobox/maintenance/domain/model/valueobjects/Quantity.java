package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Quantity {
    private Integer value;
    private String unit;

    protected Quantity() {}

    public Quantity(Integer value, String unit) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.value = value;
        this.unit = unit;
    }
}