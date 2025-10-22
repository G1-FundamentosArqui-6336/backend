package org.upc.cobox.fleet.domain.services;

import org.upc.cobox.fleet.domain.model.commands.CreateDriverCommand;
import org.upc.cobox.fleet.domain.model.commands.UpdateDriverStatusOnCompletedRouteCommand;

public interface DriverCommandService {
    Long handle(CreateDriverCommand command);
    void handle(UpdateDriverStatusOnCompletedRouteCommand command);
}
