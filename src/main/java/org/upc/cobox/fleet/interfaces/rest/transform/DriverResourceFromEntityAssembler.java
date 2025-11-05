package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.aggregates.Driver;

import org.upc.cobox.fleet.interfaces.rest.resources.DriverResource;

public class DriverResourceFromEntityAssembler {
    public static DriverResource toResourceFromEntity(Driver entity){
        return new DriverResource(
                entity.getId(),
                entity.getLicenceNumber(),
                entity.getDriverStatus().name()
        );
    }
}