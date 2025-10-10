package org.upc.cobox.delivery.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class ScheduledAt {
    @Getter private Date scheduledAt;
    protected ScheduledAt() {}
    public ScheduledAt(Date scheduledAt) {
        if (scheduledAt == null) throw new IllegalArgumentException("Fecha programada requerida");
        this.scheduledAt = scheduledAt;
    }
    @Override public boolean equals(Object o){ return o instanceof ScheduledAt s && Objects.equals(scheduledAt, s.scheduledAt);}
    @Override public int hashCode(){ return Objects.hash(scheduledAt); }
}