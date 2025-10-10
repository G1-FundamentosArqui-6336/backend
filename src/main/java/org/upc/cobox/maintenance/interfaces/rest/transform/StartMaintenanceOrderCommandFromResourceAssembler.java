package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.StartMaintenanceOrderCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.StartMaintenanceOrderResource;

public class StartMaintenanceOrderCommandFromResourceAssembler {
    public static StartMaintenanceOrderCommand toCommand(StartMaintenanceOrderResource r) {
        return new StartMaintenanceOrderCommand(
                r.maintenanceOrderId(),
                r.vehicleId(),
                r.technicianId()
        );
    }
}
