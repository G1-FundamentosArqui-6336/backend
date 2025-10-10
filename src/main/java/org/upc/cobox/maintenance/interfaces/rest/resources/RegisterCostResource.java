package org.upc.cobox.maintenance.interfaces.rest.resources;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record RegisterCostResource(
        @NotNull Long maintenanceOrderId,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount,
        @NotBlank String currency
) {}
