package org.upc.cobox.iam.interfaces.rest.transform;

import org.upc.cobox.iam.domain.model.commands.SignUpCommand;
import org.upc.cobox.iam.domain.model.entities.Role;
import org.upc.cobox.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null
                ? resource.roles().stream().map(Role::toRoleFromName).toList()
                : new ArrayList<Role>();
        return new SignUpCommand(resource.email(), resource.password(), resource.firstName(), resource.lastName(), resource.phone(), roles);
    }
}
