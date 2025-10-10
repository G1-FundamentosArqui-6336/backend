package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record ScheduleMaintenanceOrderResource(
        @NotNull Long maintenanceOrderId,
        @NotNull Long vehicleId,
        @NotBlank String startTime, // ISO_LOCAL_DATE_TIME
        @NotBlank String endTime    // ISO_LOCAL_DATE_TIME
) {}
