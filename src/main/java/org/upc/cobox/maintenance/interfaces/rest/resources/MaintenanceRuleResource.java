package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MaintenanceRuleResource(
        @NotBlank String criteria,              // "TIME" | "MILEAGE" | "MIXED"
        @Nullable @Positive Integer everyMonths,
        @Nullable @Positive Integer everyKilometers,
        @Nullable @Min(0) Integer tolerancePercentage, // ignored on create/update (VO sets default), echoed in responses
        @NotBlank String maintenanceType,       // "PREDICTIVE" | "PREVENTIVE" | "CORRECTIVE"
        @NotBlank String defaultPriority        // "HIGH" | "MEDIUM" | "LOW"
) {}
