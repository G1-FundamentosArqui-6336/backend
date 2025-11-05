package org.upc.cobox.fleet.domain.model.commands;

public record AssignVehicleToRouteCommand(Long routeId, Long vehicleId) {
}
