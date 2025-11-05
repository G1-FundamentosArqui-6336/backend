package org.upc.cobox.fleet.interfaces.rest.transform;


import org.upc.cobox.fleet.domain.model.commands.CreateDriverCommand;
import org.upc.cobox.fleet.interfaces.rest.resources.CreateDriverResource;

public class CreateDriverCommandFromResourceAssembler {
    public static CreateDriverCommand toCommandFromResource(CreateDriverResource resource){
        return new CreateDriverCommand(
                resource.licenceNumber()
        );
    }
}
