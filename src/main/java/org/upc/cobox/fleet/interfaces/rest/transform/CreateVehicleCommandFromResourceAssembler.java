package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.commands.CreateVehicleCommand;
import org.upc.cobox.fleet.interfaces.rest.resources.CreateVehicleResource;

public class CreateVehicleCommandFromResourceAssembler {
    public static CreateVehicleCommand toCommandFromResource(CreateVehicleResource resource){
        return new CreateVehicleCommand(
                resource.plateNumber(),
                resource.capacityKg()
        );
    }
}


