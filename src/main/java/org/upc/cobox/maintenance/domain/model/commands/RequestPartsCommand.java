package org.upc.cobox.maintenance.domain.model.commands;

public record RequestPartsCommand(
        Long maintenanceOrderId,
        Long supplierId,
        String partNumber,
        String description,
        Integer quantity,
        String unit
) {
}
