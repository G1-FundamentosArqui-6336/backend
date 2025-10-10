package org.upc.cobox.maintenance.domain.model.commands;

public record ScheduleMaintenanceOrderCommand(
        Long vehicleId,
        Long maintenanceOrderId,
        String startTime,
        String endTime
) {
}
