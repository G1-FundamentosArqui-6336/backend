package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record StartMaintenanceOrderResource(
        @NotNull Long maintenanceOrderId,
        @NotNull Long vehicleId,
        @NotNull Long technicianId
) {}
