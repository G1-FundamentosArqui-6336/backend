package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.interfaces.rest.resources.RouteAssignmentResource;

public class RouteAssignmentResourceFromEntityAssembler {
    public static RouteAssignmentResource toResource(RouteAssignment a){
        return new RouteAssignmentResource(
                a.getId(), a.getRouteId(), a.getStatus().name(),
                a.getAssignedAt(), a.getPlannedStart(), a.getStartedAt(), a.getCompletedAt());
    }
}
