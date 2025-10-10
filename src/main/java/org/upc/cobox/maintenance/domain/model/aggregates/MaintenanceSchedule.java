package org.upc.cobox.maintenance.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.maintenance.domain.model.events.RulesUpdatedEvent;
import org.upc.cobox.maintenance.domain.model.events.ScheduleActivatedEvent;
import org.upc.cobox.maintenance.domain.model.events.ScheduleDeactivatedEvent;
import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceRule;
import org.upc.cobox.maintenance.domain.model.valueobjects.VehicleId;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MaintenanceSchedule extends AuditableAbstractAggregateRoot<MaintenanceSchedule> {

    @Embedded
    VehicleId vehicleId;

    @Column(nullable = false, length = 20)
    String status; //ACTIVE, INACTIVE

    @ElementCollection
    @CollectionTable(name = "maintenance_rules", joinColumns = @JoinColumn(name = "schedule_id"))
    private List<MaintenanceRule> rules = new ArrayList<>();

    private LocalDateTime lastEvaluationAt;

    private LocalDateTime nextEvaluationAt;

    protected MaintenanceSchedule() {
        // Required by JPA
    }

    public MaintenanceSchedule(VehicleId vehicleId, List<MaintenanceRule> rules) {
        this.vehicleId = vehicleId;
        this.rules = new ArrayList<>(rules);
        this.status = "ACTIVE";
        this.nextEvaluationAt = LocalDateTime.now();
    }

    public void activate() {
        this.status = "ACTIVE";
        registerEvent(new ScheduleActivatedEvent(getId(), vehicleId.vehicleId(), LocalDateTime.now()));
    }

    public void deactivate() {
        this.status = "INACTIVE";
        registerEvent(new ScheduleDeactivatedEvent(getId(), vehicleId.vehicleId(), LocalDateTime.now()));
    }

    public void updateRules(List<MaintenanceRule> newRules) {
        if (newRules == null || newRules.isEmpty()) {
            throw new IllegalArgumentException("Rules cannot be empty");
        }
        this.rules.clear();
        this.rules.addAll(newRules);
        // Emit Event
        registerEvent(new RulesUpdatedEvent(getId(), vehicleId.vehicleId(), LocalDateTime.now()));
    }

    public List<MaintenanceOrder> evaluateDueOrders(int currentOdometer, LocalDateTime currentDate) {
        if (!"ACTIVE".equals(this.status)) {
            return List.of();
        }

        this.lastEvaluationAt = LocalDateTime.now();
        this.nextEvaluationAt = calculateNextEvaluation();

        List<MaintenanceOrder> orders = new ArrayList<>();
        for (MaintenanceRule rule : rules) {
            if (rule.isDue(currentOdometer, currentDate, lastEvaluationAt, null)) {
                MaintenanceOrder order = new MaintenanceOrder(
                        vehicleId,
                        rule.getMaintenanceType().name(),
                        rule.getDefaultPriority().name(),
                        rule.getCriteria(),
                        String.valueOf(currentOdometer),
                        null // scheduledTimelapse es null inicialmente, se programa después
                );
                orders.add(order);
            }
        }
        return orders;
    }

    private LocalDateTime calculateNextEvaluation() {
        return LocalDateTime.now().plusDays(1); // Simple strategy
    }
}
