package org.upc.cobox.fleet.domain.exceptions;

import org.upc.cobox.fleet.domain.model.valueobjects.DriverStatus;

public class DriverNotInRouteException extends RuntimeException {
    public DriverNotInRouteException(DriverStatus driverStatus) {
        super("Driver with status: " + driverStatus + " is not in route.");
    }
}