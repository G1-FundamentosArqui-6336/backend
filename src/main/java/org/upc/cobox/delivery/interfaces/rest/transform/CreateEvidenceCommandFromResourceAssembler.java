package org.upc.cobox.delivery.interfaces.rest.transform;


import org.upc.cobox.delivery.domain.model.commands.CreateEvidenceCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.CreateEvidenceResource;

public class CreateEvidenceCommandFromResourceAssembler {

    public static CreateEvidenceCommand toCommandFromResource(CreateEvidenceResource resource) {
        return new CreateEvidenceCommand(
                resource.receiverName(),
                resource.photoUrl()
        );
    }
}


