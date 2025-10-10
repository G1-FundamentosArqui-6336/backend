package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class Reference {
    @Getter private String reference;

    protected Reference() {}
    public Reference(String reference) {
        this.reference = reference == null ? "" : reference.trim();
    }
    @Override public boolean equals(Object o){ return o instanceof Reference r && Objects.equals(reference, r.reference);}
    @Override public int hashCode(){ return Objects.hash(reference); }
}