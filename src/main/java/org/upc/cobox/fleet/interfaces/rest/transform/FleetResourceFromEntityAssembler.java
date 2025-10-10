package org.upc.cobox.fleet.interfaces.rest.transform;

import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.interfaces.rest.resources.FleetResource;

public class FleetResourceFromEntityAssembler {
    public static FleetResource toResource(Fleet f){
        return new FleetResource(
                f.getId(), f.getPlaca().getValue(), f.getMarca(), f.getModelo(),
                f.getCapacidadKg(), f.getEstado().name());
    }
}
