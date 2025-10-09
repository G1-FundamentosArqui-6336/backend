package org.upc.cobox.fleet.domain.services;

import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.domain.model.commands.*;

import java.util.Optional;

public interface FleetCommandService {
    Optional<Fleet> handle(CreateFleetCommand c);
    Optional<Fleet> handle(UpdateFleetCommand c);
    void handleDelete(Long fleetId);
    Optional<Fleet> handle(ChangeFleetStatusCommand c);

    // asignaciones
    Optional<RouteAssignment> handle(AssignRouteToFleetCommand c);
    Optional<Fleet> handle(StartRouteAssignmentCommand c);
    Optional<Fleet> handle(CompleteRouteAssignmentCommand c);
    boolean handle(CheckFleetByIdAndCapacityCommand command);
}
