package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TechnicianId(Long technicianId) {
    public TechnicianId {
        if (technicianId < 0) {
            throw new IllegalArgumentException("TechnicianId cannot be negative");
        }
    }
    public TechnicianId() { this(0L); }
}