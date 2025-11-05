package org.upc.cobox.fleet.interfaces.rest.transform;


import org.upc.cobox.fleet.domain.model.commands.CreateRouteCommand;
import org.upc.cobox.fleet.interfaces.rest.resources.CreateRouteResource;

public class CreateRouteCommandFromResourceAssembler {
    public static CreateRouteCommand toCommandFromResource(CreateRouteResource resource){
        return new CreateRouteCommand(
                resource.title()
        );
    }
}
