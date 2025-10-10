package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.RegisterCostCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.RegisterCostResource;

public class RegisterCostCommandFromResourceAssembler {
    public static RegisterCostCommand toCommand(RegisterCostResource r) {
        return new RegisterCostCommand(
                r.maintenanceOrderId(),
                r.amount().doubleValue(),
                r.currency()
        );
    }
}
