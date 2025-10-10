package org.upc.cobox.maintenance.domain.model.commands;

public record RegisterJobCommand(
        Long maintenanceOrderId,
        String description,
        Integer estimatedDuration,
        Long technicianId,
        String partNumber
) {
}
