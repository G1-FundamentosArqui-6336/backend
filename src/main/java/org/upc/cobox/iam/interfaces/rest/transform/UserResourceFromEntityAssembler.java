package org.upc.cobox.iam.interfaces.rest.transform;

import org.upc.cobox.iam.domain.model.aggregates.User;
import org.upc.cobox.iam.domain.model.entities.Role;
import org.upc.cobox.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        var roles = entity.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(entity.getId(), entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.getPhone(), roles);
    }
}
