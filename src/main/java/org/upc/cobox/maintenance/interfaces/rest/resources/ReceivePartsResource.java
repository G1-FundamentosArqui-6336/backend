package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record ReceivePartsResource(
        @NotNull Long maintenanceOrderId,
        @NotBlank String partNumber
) {}
