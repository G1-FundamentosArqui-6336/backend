package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class RouteId {
    @Getter
    private Long value;

    protected RouteId() {}

    public RouteId(Long value) {
        if (value == null || value <= 0) throw new IllegalArgumentException("RouteId inválido");
        this.value = value;
    }

    @Override public boolean equals(Object o) { return o instanceof RouteId f && Objects.equals(value, f.value); }
    @Override public int hashCode() { return Objects.hash(value); }
}