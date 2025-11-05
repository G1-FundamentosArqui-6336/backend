package org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.fleet.domain.model.aggregates.Vehicle;



@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
