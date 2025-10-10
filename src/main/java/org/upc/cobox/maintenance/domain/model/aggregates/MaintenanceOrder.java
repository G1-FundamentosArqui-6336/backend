package org.upc.cobox.maintenance.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.maintenance.domain.model.entities.Job;
import org.upc.cobox.maintenance.domain.model.entities.PartsRequest;
import org.upc.cobox.maintenance.domain.model.events.*;
import org.upc.cobox.maintenance.domain.model.valueobjects.*;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MaintenanceOrder extends AuditableAbstractAggregateRoot<MaintenanceOrder> {
    @Embedded
    @Column(nullable = false)
    private VehicleId vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MaintenanceTypes maintenanceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Priorities priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MaintenanceOrderStatus status; // OPEN → SCHEDULED → IN_PROGRESS → COMPLETED / CANCELLED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Reason reason; // TIME, MILEAGE, ALERT

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "km", column = @Column(name = "opening_km", nullable = false))
    })
    private Odometer openingOdometer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "km", column = @Column(name = "closing_km"))
    })
    private Odometer closingOdometer;

    @Embedded
    private Timelapse scheduledTimelapse;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "maintenance_order_id", nullable = false)
    private List<Job> jobs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "maintenance_order_id", nullable = false)
    private List<PartsRequest> partsRequests = new ArrayList<>();

    @Embedded
    private Money totalCost;

    private Long technicianId;

    protected MaintenanceOrder() {
    }

    public MaintenanceOrder(VehicleId vehicleId, String maintenanceType, String priority, String reason, String openingOdometer, Timelapse scheduledTimelapse) {
        this.vehicleId = vehicleId;
        this.maintenanceType = MaintenanceTypes.valueOf(maintenanceType.toUpperCase());
        this.priority = Priorities.valueOf(priority.toUpperCase());
        this.status = MaintenanceOrderStatus.OPEN;
        this.reason = Reason.valueOf(reason.toUpperCase());
        this.openingOdometer = new Odometer(Integer.parseInt(openingOdometer));
        this.scheduledTimelapse = scheduledTimelapse;
        this.totalCost = new Money(BigDecimal.ZERO, "USD");
        // Emit domain event
        registerEvent(new MaintenanceOrderCreatedEvent(
                getId(),
                vehicleId.vehicleId(),
                maintenanceType,
                reason,
                priority,
                LocalDateTime.now()
        ));
    }

    public void scheduleMaintenanceOrder(Timelapse scheduledTimelapse, boolean hasConflictingOrders) {
        if (!MaintenanceOrderStatus.OPEN.equals(this.status)) {
            throw new IllegalStateException("Can only schedule an OPEN order");
        }
        // Check for conflicting orders for the same vehicle and maintenance type.
        if (hasConflictingOrders) {
            throw new IllegalStateException(
                    "Cannot schedule: another order exists for this vehicle and maintenance type"
            );
        }

        this.scheduledTimelapse = scheduledTimelapse;
        this.status = MaintenanceOrderStatus.SCHEDULED;

        // Emit domain event
        registerEvent(new MaintenanceOrderScheduledEvent(
                getId(),
                scheduledTimelapse.getStartTime(),
                scheduledTimelapse.getEndTime(),
                LocalDateTime.now()
        ));
    }

    public void startMaintenanceOrder(Long technicianId, boolean hasOverlappingTimeslots) {
        if (!MaintenanceOrderStatus.SCHEDULED.equals(this.status)) {
            throw new IllegalStateException("Can only start a SCHEDULED order");
        }

        if (this.scheduledTimelapse == null) {
            throw new IllegalStateException("Cannot start order without a scheduled timeslapse");
        }

        if (hasOverlappingTimeslots) {
            throw new IllegalStateException(
                    "Cannot start: timeslapse overlaps with another active maintenance order"
            );
        }

        this.technicianId = technicianId;
        this.status = MaintenanceOrderStatus.IN_PROGRESS;

        // Emit domain event if needed
        registerEvent(new MaintenanceOrderStartedEvent(
                getId(),
                technicianId,
                LocalDateTime.now()
        ));
    }

    public void registerJob(Job job) {
        if (!MaintenanceOrderStatus.IN_PROGRESS.equals(this.status)) {
            throw new IllegalStateException("Can only register jobs for IN_PROGRESS orders");
        }
        this.jobs.add(job);
    }

    public void requestParts(PartsRequest partsRequest) {
        this.partsRequests.add(partsRequest);
        // Emit domain event
        registerEvent(new PartsRequestedEvent(
                getId(),
                partsRequest.getPartNumber(),
                partsRequest.getQuantity().getValue(),
                LocalDateTime.now()
        ));
    }

    public void receiveParts(String partNumber) {
        PartsRequest request = partsRequests.stream()
                .filter(pr -> pr.getPartNumber().equals(partNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Parts request not found"));
        request.markAsReceived();
        // Emit domain event
        registerEvent(new PartsReceivedEvent(
                getId(),
                partNumber,
                LocalDateTime.now()
        ));
    }

    public void registerCost(Money additionalCost) {
        if (!this.totalCost.getCurrency().equals(additionalCost.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        this.totalCost = this.totalCost.add(additionalCost);
    }

    public void completeMaintenanceOrder(String closingOdometer) {
        if (!MaintenanceOrderStatus.IN_PROGRESS.equals(this.status)) {
            throw new IllegalStateException("Can only complete IN_PROGRESS orders");
        }

        int closingKm = Integer.parseInt(closingOdometer);
        if (closingKm < this.openingOdometer.km()) {
            throw new IllegalStateException("Closing odometer must be greater than or equal to opening odometer");
        }

        boolean allJobsCompleted = jobs.stream()
                .allMatch(Job::isCompleted);
        if (!allJobsCompleted) {
            throw new IllegalStateException("All jobs must be completed");
        }

        boolean allPartsReceived = partsRequests.isEmpty() ||
                partsRequests.stream().allMatch(PartsRequest::isReceived);
        if (!allPartsReceived) {
            throw new IllegalStateException("Parts pending");
        }

        this.closingOdometer = new Odometer(closingKm);
        this.status = MaintenanceOrderStatus.COMPLETED;

        // Emit domain event
        registerEvent(new MaintenanceOrderCompletedEvent(
                getId(),
                totalCost.getAmount(),
                closingOdometer,
                LocalDateTime.now()
        ));
    }

    public void cancelMaintenanceOrder(String cancelReason) {
        if (MaintenanceOrderStatus.COMPLETED.equals(this.status)) {
            throw new IllegalStateException("Cannot cancel a COMPLETED order");
        }
        this.status = MaintenanceOrderStatus.CANCELLED;
        this.reason = Reason.valueOf(cancelReason);

        // Emit domain event if needed
        registerEvent(new MaintenanceOrderCancelledEvent(
                getId(),
                cancelReason,
                LocalDateTime.now()
        ));
    }
}
