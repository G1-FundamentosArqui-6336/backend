package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EvaluateMaintenanceScheduleResource(
        @NotNull Long scheduleId,
        @Min(0) Integer currentKm,                 // nullable if only time-based rules
        @NotNull LocalDateTime evaluationTime      // ISO_LOCAL_DATE_TIME
) {}
