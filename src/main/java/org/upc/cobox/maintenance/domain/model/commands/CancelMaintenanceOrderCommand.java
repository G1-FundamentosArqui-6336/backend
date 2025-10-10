package org.upc.cobox.maintenance.domain.model.commands;

public record CancelMaintenanceOrderCommand(
        Long maintenanceOrderId,
        String reason
) {
}
