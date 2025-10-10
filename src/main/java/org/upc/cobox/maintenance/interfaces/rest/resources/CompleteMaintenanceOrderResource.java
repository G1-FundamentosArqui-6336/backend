package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record CompleteMaintenanceOrderResource(
        @NotNull Long maintenanceOrderId,
        @Min(0) int closingOdometer
) {}
