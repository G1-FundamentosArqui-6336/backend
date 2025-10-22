package org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.fleet.domain.model.aggregates.Driver;
import org.upc.cobox.fleet.domain.model.aggregates.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByDriver(Driver driver);

}
