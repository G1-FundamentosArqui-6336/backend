package org.upc.cobox.fleet.domain.services;

import org.upc.cobox.fleet.domain.model.aggregates.Driver;
import org.upc.cobox.fleet.domain.model.queries.GetAllDriversQuery;
import org.upc.cobox.fleet.domain.model.queries.GetDriverByIdQuery;

import java.util.List;
import java.util.Optional;

public interface DriverQueryService {

    List<Driver> handle(GetAllDriversQuery query);
    Optional<Driver> handle(GetDriverByIdQuery query);
}
