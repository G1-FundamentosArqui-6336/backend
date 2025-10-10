package org.upc.cobox.fleet.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle>{


    private String plateNumber;
    private double capacityKg;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;


    public boolean isAvailable() { return status == VehicleStatus.AVAILABLE; }

    public void markInUse() { this.status = VehicleStatus.IN_USE; }
    public void markAvailable() { this.status = VehicleStatus.AVAILABLE; }

    public enum VehicleStatus { AVAILABLE, IN_USE, MAINTENANCE }
}
