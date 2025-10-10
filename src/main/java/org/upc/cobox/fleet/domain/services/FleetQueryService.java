package org.upc.cobox.fleet.domain.services;

import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface FleetQueryService {
    Optional<Fleet> handle(GetFleetByIdQuery q);
    List<Fleet> handle(GetAllFleetsQuery q);
    List<Fleet> handle(GetFleetsByEstadoQuery q);
    List<RouteAssignment> handle(GetAssignmentsByFleetQuery q);
}
