package org.upc.cobox.maintenance.domain.model.commands;

public record StartMaintenanceOrderCommand(
        Long vehicleId,
        Long maintenanceOrderId,
        Long technicianId
) {
}
