package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.commands.RegisterJobCommand;
import org.upc.cobox.maintenance.interfaces.rest.resources.RegisterJobResource;

public class RegisterJobCommandFromResourceAssembler {
    public static RegisterJobCommand toCommand(RegisterJobResource r) {
        return new RegisterJobCommand(
                r.maintenanceOrderId(),
                r.description(),
                r.estimatedDuration(),
                r.technicianId(),
                r.partNumber()
        );
    }
}
