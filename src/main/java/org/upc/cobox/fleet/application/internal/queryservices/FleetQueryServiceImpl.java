package org.upc.cobox.fleet.application.internal.queryservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.domain.model.queries.*;
import org.upc.cobox.fleet.domain.services.FleetQueryService;
import org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories.FleetRepository;
import org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories.RouteAssignmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FleetQueryServiceImpl implements FleetQueryService {

    private final FleetRepository fleetRepo;
    private final RouteAssignmentRepository assignmentRepo;

    @Override
    public Optional<Fleet> handle(GetFleetByIdQuery q) { return fleetRepo.findById(q.fleetId()); }

    @Override
    public List<Fleet> handle(GetAllFleetsQuery q) { return fleetRepo.findAll(); }

    @Override
    public List<Fleet> handle(GetFleetsByEstadoQuery q) { return fleetRepo.findByEstado(q.estado()); }

    @Override
    public List<RouteAssignment> handle(GetAssignmentsByFleetQuery q) {
        return assignmentRepo.findByFleet_Id(q.fleetId());
    }
}
