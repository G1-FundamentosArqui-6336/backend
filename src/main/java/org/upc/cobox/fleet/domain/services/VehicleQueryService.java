package org.upc.cobox.fleet.domain.services;


import org.upc.cobox.fleet.domain.model.aggregates.Vehicle;
import org.upc.cobox.fleet.domain.model.queries.GetAllVehiclesQuery;
import org.upc.cobox.fleet.domain.model.queries.GetVehicleByIdQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {

    List<Vehicle> handle(GetAllVehiclesQuery query);
    Optional<Vehicle> handle(GetVehicleByIdQuery query);

}
