package org.upc.cobox.maintenance.domain.model.commands;

public record ReceivePartsCommand(
        Long maintenanceOrderId,
        String partNumber
) {
}
