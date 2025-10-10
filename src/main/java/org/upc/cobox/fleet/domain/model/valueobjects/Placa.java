package org.upc.cobox.fleet.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class Placa {
    @NotBlank
    private String value;

    protected Placa() {}

    public Placa(String value) {
        var v = value == null ? "" : value.trim().toUpperCase();
        if (!v.matches("^[A-Z0-9]{3}-[0-9A-Z]{3}$"))
            throw new IllegalArgumentException("Placa inválida. Formato esperado ABC-123");
        this.value = v;
    }

    public String getValue() { return value; }
    @Override public String toString(){ return value; }
    @Override public boolean equals(Object o){ return o instanceof Placa p && Objects.equals(value,p.value); }
    @Override public int hashCode(){ return Objects.hash(value); }
}
