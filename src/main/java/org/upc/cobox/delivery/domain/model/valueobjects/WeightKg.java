package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class WeightKg {
    @Getter private BigDecimal weightKg;
    protected WeightKg() {}
    public WeightKg(BigDecimal weightKg) {
        if (weightKg == null || weightKg.signum() < 0) throw new IllegalArgumentException("Peso inválido");
        this.weightKg = weightKg;
    }
    @Override public boolean equals(Object o){ return o instanceof WeightKg w && Objects.equals(weightKg, w.weightKg);}
    @Override public int hashCode(){ return Objects.hash(weightKg); }
}