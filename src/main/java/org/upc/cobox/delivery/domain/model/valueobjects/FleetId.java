package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class FleetId {
    @Getter
    private Long value;

    protected FleetId() {}

    public FleetId(Long value) {
        if (value == null || value <= 0) throw new IllegalArgumentException("FleetId inválido");
        this.value = value;
    }

    @Override public boolean equals(Object o) { return o instanceof FleetId f && Objects.equals(value, f.value); }
    @Override public int hashCode() { return Objects.hash(value); }
}
