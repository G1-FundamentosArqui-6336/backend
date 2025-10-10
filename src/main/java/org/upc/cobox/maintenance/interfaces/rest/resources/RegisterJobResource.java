package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record RegisterJobResource(
        @NotNull Long maintenanceOrderId,
        @NotBlank String description,
        @Min(1) int estimatedDuration,
        Long technicianId,
        String partNumber
) {}
