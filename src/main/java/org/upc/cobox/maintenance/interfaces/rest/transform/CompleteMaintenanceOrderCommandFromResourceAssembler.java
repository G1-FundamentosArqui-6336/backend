// interfaces/rest/transform/CompleteMaintenanceOrderCommandFromResourceAssembler.java
package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.CompleteMaintenanceOrderCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.CompleteMaintenanceOrderResource;

public class CompleteMaintenanceOrderCommandFromResourceAssembler {
    public static CompleteMaintenanceOrderCommand toCommand(CompleteMaintenanceOrderResource r) {
        return new CompleteMaintenanceOrderCommand(
                r.maintenanceOrderId(),
                r.closingOdometer()
        );
    }
}
