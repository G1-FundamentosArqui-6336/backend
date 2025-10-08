package org.upc.cobox.iam.interfaces.rest.transform;

import org.upc.cobox.iam.domain.model.aggregates.User;
import org.upc.cobox.iam.domain.model.entities.Role;
import org.upc.cobox.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        Set<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getStringName)
                .collect(Collectors.toSet());
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), token, roleNames);
    }
}

