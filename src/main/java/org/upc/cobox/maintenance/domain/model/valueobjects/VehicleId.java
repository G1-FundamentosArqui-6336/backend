package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleId(Long vehicleId) {
    public VehicleId {
        if (vehicleId < 0) {
            throw new IllegalArgumentException("VehicleId cannot be negative");
        }
    }
    public VehicleId() { this(0L); }
}

