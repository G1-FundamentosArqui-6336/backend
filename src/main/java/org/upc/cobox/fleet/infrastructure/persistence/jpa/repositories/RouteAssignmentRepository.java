package org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteAssignmentRepository extends JpaRepository<RouteAssignment, Long> {
    List<RouteAssignment> findByFleet_Id(Long fleetId);
    Optional<RouteAssignment> findByIdAndFleet_Id(Long id, Long fleetId);
}
