package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.RouteStatus;

public class RouteNotPlannedException extends RuntimeException {
    public RouteNotPlannedException(RouteStatus actualStatus) {
        super("Route must be in PLANNED status to perform this action, but was: " + actualStatus);
    }
}