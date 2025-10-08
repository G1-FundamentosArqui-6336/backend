package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.commands.UpdateFleetCommand;
import org.upc.cobox.fleet.interfaces.rest.resources.UpdateFleetResource;

public class UpdateFleetCommandFromResourceAssembler {
    public static UpdateFleetCommand toCommand(Long id, UpdateFleetResource r){
        return new UpdateFleetCommand(id, r.marca(), r.modelo(), r.capacidadKg());
    }
}
