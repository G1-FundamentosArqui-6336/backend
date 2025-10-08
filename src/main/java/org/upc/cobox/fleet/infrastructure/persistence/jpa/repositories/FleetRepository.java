package org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.domain.model.valueobjects.Estado;

import java.util.List;
import java.util.Optional;

@Repository
public interface FleetRepository extends JpaRepository<Fleet, Long> {
    Optional<Fleet> findByPlaca_Value(String placa);
    List<Fleet> findByEstado(Estado estado);
}
