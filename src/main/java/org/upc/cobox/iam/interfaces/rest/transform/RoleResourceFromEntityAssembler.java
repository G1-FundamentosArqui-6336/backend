package org.upc.cobox.iam.interfaces.rest.transform;

import org.upc.cobox.iam.domain.model.entities.Role;
import org.upc.cobox.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(entity.getId(), entity.getStringName());
    }
}
