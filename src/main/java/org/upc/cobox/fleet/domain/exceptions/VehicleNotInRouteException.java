package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.VehicleStatus;

public class VehicleNotInRouteException extends RuntimeException {
    public VehicleNotInRouteException(VehicleStatus vehicleStatus) {
        super("Vehicle with status: " + vehicleStatus + " is not in route.");
    }
}