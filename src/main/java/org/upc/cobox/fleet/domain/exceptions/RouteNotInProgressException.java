package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.RouteStatus;

public class RouteNotInProgressException extends RuntimeException {
    public RouteNotInProgressException(RouteStatus actualStatus) {
        super("Route must be in IN_PROGRESS status to perform this action, but was: " + actualStatus);
    }
}