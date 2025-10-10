package org.upc.cobox.maintenance.domain.model.commands;

import org.upc.cobox.maintenance.domain.model.valueobjects.MaintenanceRule;

import java.util.List;

public record CreateMaintenanceScheduleCommand(
        Long vehicleId,
        List<MaintenanceRule> rules
) {}