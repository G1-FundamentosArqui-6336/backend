package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.CreateMaintenanceOrderCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.CreateMaintenanceOrderResource;

public class CreateMaintenanceOrderCommandFromResourceAssembler {
    public static CreateMaintenanceOrderCommand toCommand(CreateMaintenanceOrderResource r) {
        return new CreateMaintenanceOrderCommand(
                r.vehicleId(),
                r.maintenanceType(),
                r.priority(),
                r.reason(),
                r.openingOdometer(),
                r.startTime(),
                r.endTime()
        );
    }
}
