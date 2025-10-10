package org.upc.cobox.fleet.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class OrderId {
    @Getter
    private Long value;

    protected OrderId() {}

    public OrderId(Long value) {
        if (value == null || value <= 0) throw new IllegalArgumentException("OrderId inválido");
        this.value = value;
    }

    @Override public boolean equals(Object o) { return o instanceof OrderId f && Objects.equals(value, f.value); }
    @Override public int hashCode() { return Objects.hash(value); }
}