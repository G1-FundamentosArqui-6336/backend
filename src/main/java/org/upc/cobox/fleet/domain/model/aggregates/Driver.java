package org.upc.cobox.fleet.domain.model.aggregates;



import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.fleet.domain.model.valueobjects.DriverStatus;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class Driver extends AuditableAbstractAggregateRoot<Driver> {


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private DriverStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    protected Driver() {
        this.status = DriverStatus.AVAILABLE;
    }



    public boolean isAvailable() { return status == DriverStatus.AVAILABLE; }

    public void markBusy() {
        if (status == DriverStatus.UNAVAILABLE) throw new IllegalStateException("Driver unavailable");
        this.status = DriverStatus.BUSY;
    }

    public void markAvailable() { this.status = DriverStatus.AVAILABLE; }

}
