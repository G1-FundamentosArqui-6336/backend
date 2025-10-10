package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record CreateMaintenanceOrderResource(
        @NotNull Long vehicleId,
        @NotBlank String maintenanceType, // PREDICTIVE|PREVENTIVE|CORRECTIVE...
        @NotBlank String priority,       // HIGH|MEDIUM|LOW...
        @NotBlank String reason,         // TIME|MILEAGE|ALERT
        @Min(0) int openingOdometer,
        String startTime, // ISO_LOCAL_DATE_TIME
        String endTime    // ISO_LOCAL_DATE_TIME
) {}
