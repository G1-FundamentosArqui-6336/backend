package org.upc.cobox.fleet.domain.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(Long routeId) {
        super("Route with ID " + routeId + " not found.");
    }
}