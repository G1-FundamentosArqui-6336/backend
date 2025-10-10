package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.ActivateMaintenanceScheduleCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.ActivateMaintenanceScheduleResource;

public class ActivateMaintenanceScheduleCommandFromResourceAssembler {
    public static ActivateMaintenanceScheduleCommand toCommand(ActivateMaintenanceScheduleResource r) {
        return new ActivateMaintenanceScheduleCommand(r.scheduleId());
    }
}
