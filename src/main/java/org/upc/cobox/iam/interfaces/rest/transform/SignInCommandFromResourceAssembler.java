package org.upc.cobox.iam.interfaces.rest.transform;

import org.upc.cobox.iam.domain.model.commands.SignInCommand;
import org.upc.cobox.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}
