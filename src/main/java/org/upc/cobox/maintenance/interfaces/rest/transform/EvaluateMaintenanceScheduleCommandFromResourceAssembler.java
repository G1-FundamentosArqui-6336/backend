package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.EvaluateMaintenanceScheduleCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.EvaluateMaintenanceScheduleResource;

public class EvaluateMaintenanceScheduleCommandFromResourceAssembler {
    public static EvaluateMaintenanceScheduleCommand toCommand(EvaluateMaintenanceScheduleResource r) {
        return new EvaluateMaintenanceScheduleCommand(
                r.scheduleId(),
                r.currentKm(),
                r.evaluationTime().toString()
        );
    }
}
