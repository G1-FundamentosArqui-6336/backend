package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.RequestPartsCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.RequestPartsResource;

public class RequestPartsCommandFromResourceAssembler {
    public static RequestPartsCommand toCommand(RequestPartsResource r) {
        return new RequestPartsCommand(
                r.maintenanceOrderId(),
                r.supplierId(),
                r.partNumber(),
                r.description(),
                r.quantity(),
                r.unit()
        );
    }
}
