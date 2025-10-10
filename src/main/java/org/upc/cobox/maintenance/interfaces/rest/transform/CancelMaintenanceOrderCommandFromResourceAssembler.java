package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.CancelMaintenanceOrderCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.CancelMaintenanceOrderResource;

public class CancelMaintenanceOrderCommandFromResourceAssembler {
    public static CancelMaintenanceOrderCommand toCommand(CancelMaintenanceOrderResource r) {
        return new CancelMaintenanceOrderCommand(
                r.maintenanceOrderId(),
                r.reason()
        );
    }
}
