package org.upc.cobox.fleet.domain.services;

import org.upc.cobox.fleet.domain.model.commands.CreateVehicleCommand;
import org.upc.cobox.fleet.domain.model.commands.UpdateVehicleStatusOnCompletedRouteCommand;

public interface VehicleCommandService {
    Long handle(CreateVehicleCommand command);
    void handle(UpdateVehicleStatusOnCompletedRouteCommand command);

}
