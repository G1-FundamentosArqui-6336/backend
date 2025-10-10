package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.DeactivateMaintenanceScheduleCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.DeactivateMaintenanceScheduleResource;

public class DeactivateMaintenanceScheduleCommandFromResourceAssembler {
    public static DeactivateMaintenanceScheduleCommand toCommand(DeactivateMaintenanceScheduleResource r) {
        return new DeactivateMaintenanceScheduleCommand(r.scheduleId());
    }
}
