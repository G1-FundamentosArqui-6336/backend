package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.ReceivePartsCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.ReceivePartsResource;

public class ReceivePartsCommandFromResourceAssembler {
    public static ReceivePartsCommand toCommand(ReceivePartsResource r) {
        return new ReceivePartsCommand(
                r.maintenanceOrderId(),
                r.partNumber()
        );
    }
}
