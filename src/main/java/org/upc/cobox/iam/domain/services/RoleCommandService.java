package org.upc.cobox.iam.domain.services;

import org.upc.cobox.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
