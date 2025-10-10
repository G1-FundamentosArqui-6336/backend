package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Embeddable
@Getter
public class MaintenanceRule {
    private String criteria; // TIME, MILEAGE, MIXED

    private Integer everyMonths;
    private Integer everyKilometers;
    private Integer tolerancePercentage;

    @Enumerated(EnumType.STRING)
    private MaintenanceTypes maintenanceType;

    @Enumerated(EnumType.STRING)
    private Priorities defaultPriority;

    protected MaintenanceRule() {
    }

    public MaintenanceRule(String criteria,
                           Integer everyMonths,
                           Integer everyKilometers,
                           Integer tolerancePercentage,
                           String maintenanceType,
                           String defaultPriority) {

        if (criteria == null) throw new IllegalArgumentException("Criteria cannot be null");
        if (everyMonths == null && everyKilometers == null) {
            throw new IllegalArgumentException("Must have at least one criterion (months or kilometers)");
        }
        this.criteria = criteria.toUpperCase();
        this.everyMonths = everyMonths;
        this.everyKilometers = everyKilometers;

        int tol = tolerancePercentage == null ? 10 : tolerancePercentage;
        if (tol < 0 || tol > 100) throw new IllegalArgumentException("tolerancePercentage must be 0..100");
        this.tolerancePercentage = tol;

        this.maintenanceType = maintenanceType == null
                ? MaintenanceTypes.PREDICTIVE
                : MaintenanceTypes.valueOf(maintenanceType.toUpperCase());

        this.defaultPriority = defaultPriority == null
                ? Priorities.MEDIUM
                : Priorities.valueOf(defaultPriority.toUpperCase());
    }

    public boolean isDue(
            int currentOdometer,
            LocalDateTime now,
            @Nullable LocalDateTime lastDoneAt,
            @Nullable Integer lastDoneOdometer
    ) {
        boolean dueTime = everyMonths != null && lastDoneAt != null &&
                ChronoUnit.MONTHS.between(lastDoneAt, now) >= everyMonths - toleranceMonths();
        boolean dueKm = everyKilometers != null && lastDoneOdometer != null &&
                (currentOdometer - lastDoneOdometer) >= thresholdKm();
        return switch (criteria) {
            case "TIME" -> dueTime;
            case "MILEAGE" -> dueKm;
            case "MIXED" -> dueTime && dueKm;
            default -> false;
        };
    }

    private int thresholdKm() {
        return (int) Math.round(everyKilometers * (1 - (tolerancePercentage / 100.0)));
    }

    private int toleranceMonths() {
        return (int) Math.floor((everyMonths != null ? everyMonths : 0) * (tolerancePercentage / 100.0));
    }
}