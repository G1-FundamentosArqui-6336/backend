package org.upc.cobox.maintenance.domain.model.commands;

public record CreateMaintenanceOrderCommand(
        Long vehicleId,
        String maintenanceType,
        String priority,
        String reason,
        Integer openingOdometer,
        String startTime,
        String endTime
) {
}
