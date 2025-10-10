package org.upc.cobox.fleet.domain.exceptions;

public class FleetNotFoundException extends RuntimeException {
    public FleetNotFoundException(Long fleetId) {
        super("Fleet with ID %s not found or capacity is not available".formatted(fleetId));
    }
}
