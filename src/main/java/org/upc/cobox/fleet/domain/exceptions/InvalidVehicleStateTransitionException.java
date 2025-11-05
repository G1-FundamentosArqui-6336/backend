package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.VehicleStatus;

public class InvalidVehicleStateTransitionException extends RuntimeException {

    public InvalidVehicleStateTransitionException(VehicleStatus vehicleStatus) {
        super("Vehicle cannot be marked operational from its current state: " + vehicleStatus);
    }
}

