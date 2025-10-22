package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.aggregates.Vehicle;
import org.upc.cobox.fleet.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle entity){
        return new VehicleResource(
                entity.getId(),
                entity.getPlateNumber(),
                entity.getCapacityKg(),
                entity.getVehicleStatus().name()
        );
    }
}
