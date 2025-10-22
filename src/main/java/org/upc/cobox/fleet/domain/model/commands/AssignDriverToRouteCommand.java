package org.upc.cobox.fleet.domain.model.commands;

public record AssignDriverToRouteCommand(Long routeId, Long driverId) {
}
