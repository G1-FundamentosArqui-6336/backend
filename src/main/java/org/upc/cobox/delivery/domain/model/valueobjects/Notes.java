package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
public class Notes {
    @Getter private String notes;
    protected Notes() {}
    public Notes(String notes) { this.notes = notes == null ? "" : notes.trim(); }
    @Override public boolean equals(Object o){ return o instanceof Notes n && Objects.equals(notes, n.notes);}
    @Override public int hashCode(){ return Objects.hash(notes); }
}
