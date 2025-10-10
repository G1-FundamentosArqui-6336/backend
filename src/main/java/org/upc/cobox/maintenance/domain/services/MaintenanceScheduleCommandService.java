package org.upc.cobox.maintenance.domain.services;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.commands.*;

import java.util.Optional;

public interface MaintenanceScheduleCommandService {
    Optional<MaintenanceSchedule> handle(CreateMaintenanceScheduleCommand command);
    Long handle(ActivateMaintenanceScheduleCommand command);
    Long handle(DeactivateMaintenanceScheduleCommand command);
    void handle(EvaluateMaintenanceScheduleCommand command);
    Long handle(UpdateMaintenanceRulesCommand command);
}
