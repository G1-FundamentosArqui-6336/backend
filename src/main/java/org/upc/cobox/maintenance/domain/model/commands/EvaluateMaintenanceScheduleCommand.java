package org.upc.cobox.maintenance.domain.model.commands;

public record EvaluateMaintenanceScheduleCommand(
        Long maintenanceScheduleId,
        Integer currentOdometer,
        String currentDate
) {
}
