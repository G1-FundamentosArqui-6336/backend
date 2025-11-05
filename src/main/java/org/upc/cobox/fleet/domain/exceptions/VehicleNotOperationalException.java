package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.VehicleStatus;

public class VehicleNotOperationalException extends RuntimeException {
    public VehicleNotOperationalException(VehicleStatus vehicleStatus) {
        super("Vehicle with status: " + vehicleStatus + " is not operational.");
    }
}