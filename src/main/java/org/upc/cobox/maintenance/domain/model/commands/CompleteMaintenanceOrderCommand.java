package org.upc.cobox.maintenance.domain.model.commands;

public record CompleteMaintenanceOrderCommand(
        Long maintenanceOrderId,
        Integer closingOdometer
) {
}
