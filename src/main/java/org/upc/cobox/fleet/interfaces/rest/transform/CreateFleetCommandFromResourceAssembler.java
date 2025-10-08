package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.commands.CreateFleetCommand;
import org.upc.cobox.fleet.domain.model.valueobjects.Placa;
import org.upc.cobox.fleet.interfaces.rest.resources.CreateFleetResource;

public class CreateFleetCommandFromResourceAssembler {
    public static CreateFleetCommand toCommand(CreateFleetResource r){
        return new CreateFleetCommand(new Placa(r.placa()), r.marca(), r.modelo(), r.capacidadKg());
    }
}
