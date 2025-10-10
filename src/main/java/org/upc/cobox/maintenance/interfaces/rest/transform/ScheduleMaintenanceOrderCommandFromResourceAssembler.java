package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.ScheduleMaintenanceOrderCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.ScheduleMaintenanceOrderResource;

public class ScheduleMaintenanceOrderCommandFromResourceAssembler {
    public static ScheduleMaintenanceOrderCommand toCommand(ScheduleMaintenanceOrderResource r) {
        return new ScheduleMaintenanceOrderCommand(
                r.maintenanceOrderId(),
                r.vehicleId(),
                r.startTime(),
                r.endTime()
        );
    }
}
