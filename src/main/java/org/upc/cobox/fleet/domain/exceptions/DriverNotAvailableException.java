package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.DriverStatus;
import org.upc.cobox.fleet.domain.model.valueobjects.VehicleStatus;

public class DriverNotAvailableException extends RuntimeException {
    public DriverNotAvailableException(DriverStatus driverStatus) {
        super("Driver with status: " + driverStatus + " is not available for a new route.");
    }
}