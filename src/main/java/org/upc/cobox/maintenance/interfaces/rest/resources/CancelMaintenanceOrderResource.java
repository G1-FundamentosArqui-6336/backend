package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record CancelMaintenanceOrderResource(
        @NotNull Long maintenanceOrderId,
        @NotBlank String reason
) {}
