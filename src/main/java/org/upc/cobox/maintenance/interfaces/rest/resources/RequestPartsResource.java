package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record RequestPartsResource(
        @NotNull Long maintenanceOrderId,
        @NotBlank String partNumber,
        @NotBlank String description,
        @Min(1) int quantity,
        @NotBlank String unit,
        Long supplierId
) {}
