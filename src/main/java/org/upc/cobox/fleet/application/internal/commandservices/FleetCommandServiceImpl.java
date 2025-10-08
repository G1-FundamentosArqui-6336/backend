package org.upc.cobox.fleet.application.internal.commandservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.upc.cobox.fleet.domain.model.aggregates.Fleet;
import org.upc.cobox.fleet.domain.model.commands.*;
import org.upc.cobox.fleet.domain.model.entities.RouteAssignment;
import org.upc.cobox.fleet.domain.services.FleetCommandService;
import org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories.FleetRepository;
import org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories.RouteAssignmentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FleetCommandServiceImpl implements FleetCommandService {

    private final FleetRepository fleetRepo;
    private final RouteAssignmentRepository assignmentRepo;

    @Override
    public Optional<Fleet> handle(CreateFleetCommand c) {
        if (fleetRepo.findByPlaca_Value(c.placa().getValue()).isPresent()) return Optional.empty();
        var fleet = new Fleet(c.placa(), c.marca(), c.modelo(), c.capacidadKg());
        return Optional.of(fleetRepo.save(fleet));
    }

    @Override
    public Optional<Fleet> handle(UpdateFleetCommand c) {
        return fleetRepo.findById(c.fleetId()).map(f -> {
            f.actualizarDatos(c.marca(), c.modelo(), c.capacidadKg());
            return fleetRepo.save(f);
        });
    }

    @Override
    public void handleDelete(Long fleetId) { fleetRepo.deleteById(fleetId); }

    @Override
    public Optional<Fleet> handle(ChangeFleetStatusCommand c) {
        return fleetRepo.findById(c.fleetId()).map(f -> {
            f.cambiarEstado(c.estado());
            return fleetRepo.save(f);
        });
    }

    @Override
    public Optional<RouteAssignment> handle(AssignRouteToFleetCommand c) {
        return fleetRepo.findById(c.fleetId()).map(f -> {
            var a = f.assignRoute(c.routeId(), c.plannedStart());
            fleetRepo.save(f);
            return assignmentRepo.save(a);
        });
    }

    @Override
    public Optional<Fleet> handle(StartRouteAssignmentCommand c) {
        return fleetRepo.findById(c.fleetId()).map(f -> {
            f.markRouteStarted(c.assignmentId());
            return fleetRepo.save(f);
        });
    }

    @Override
    public Optional<Fleet> handle(CompleteRouteAssignmentCommand c) {
        return fleetRepo.findById(c.fleetId()).map(f -> {
            f.markRouteCompleted(c.assignmentId());
            return fleetRepo.save(f);
        });
    }
}
