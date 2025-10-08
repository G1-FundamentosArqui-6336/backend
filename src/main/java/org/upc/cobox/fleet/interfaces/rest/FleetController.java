package org.upc.cobox.fleet.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.upc.cobox.fleet.domain.model.commands.*;
import org.upc.cobox.fleet.domain.model.queries.*;
import org.upc.cobox.fleet.domain.model.valueobjects.Estado;
import org.upc.cobox.fleet.domain.services.FleetCommandService;
import org.upc.cobox.fleet.domain.services.FleetQueryService;
import org.upc.cobox.fleet.interfaces.rest.resources.*;
import org.upc.cobox.fleet.interfaces.rest.transform.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/fleets")
@Tag(name = "Fleets")
@RequiredArgsConstructor
public class FleetController {

    private final FleetCommandService commandService;
    private final FleetQueryService queryService;

    // READ (abre según tu política)
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Estado estado){
        var list = (estado == null)
                ? queryService.handle(new GetAllFleetsQuery())
                : queryService.handle(new GetFleetsByEstadoQuery(estado));
        return ResponseEntity.ok(list.stream().map(FleetResourceFromEntityAssembler::toResource).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return queryService.handle(new GetFleetByIdQuery(id))
                .map(FleetResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CreateFleetResource resource){
        var cmd = CreateFleetCommandFromResourceAssembler.toCommand(resource);
        var created = commandService.handle(cmd);
        if (created.isEmpty()) return ResponseEntity.badRequest().body("Placa duplicada");
        var res = FleetResourceFromEntityAssembler.toResource(created.get());
        return ResponseEntity.created(URI.create("/api/v1/fleets/" + res.id())).body(res);
    }

    // UPDATE (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateFleetResource resource){
        var cmd = UpdateFleetCommandFromResourceAssembler.toCommand(id, resource);
        return commandService.handle(cmd)
                .map(FleetResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PATCH estado (solo ADMIN)
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody ChangeStatusResource r){
        var cmd = new ChangeFleetStatusCommand(id, Estado.valueOf(r.estado().toUpperCase()));
        return commandService.handle(cmd)
                .map(FleetResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        commandService.handleDelete(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Asignaciones de ruta --------

    @GetMapping("/{fleetId}/assignments")
    public ResponseEntity<?> getAssignments(@PathVariable Long fleetId){
        var list = queryService.handle(new GetAssignmentsByFleetQuery(fleetId));
        return ResponseEntity.ok(list.stream().map(RouteAssignmentResourceFromEntityAssembler::toResource).toList());
    }

    @PostMapping("/{fleetId}/assignments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assign(@PathVariable Long fleetId, @RequestBody AssignRouteResource r){
        var created = commandService.handle(new AssignRouteToFleetCommand(fleetId, r.routeId(), r.plannedStart()));
        return created.isEmpty() ? ResponseEntity.badRequest().build()
                : ResponseEntity.created(URI.create("/api/v1/fleets/%d/assignments/%d"
                .formatted(fleetId, created.get().getId()))).body(RouteAssignmentResourceFromEntityAssembler.toResource(created.get()));
    }

    @PatchMapping("/{fleetId}/assignments/{assignmentId}/start")
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER')")
    public ResponseEntity<?> start(@PathVariable Long fleetId, @PathVariable Long assignmentId){
        return commandService.handle(new StartRouteAssignmentCommand(fleetId, assignmentId))
                .map(FleetResourceFromEntityAssembler::toResource).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{fleetId}/assignments/{assignmentId}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER')")
    public ResponseEntity<?> complete(@PathVariable Long fleetId, @PathVariable Long assignmentId){
        return commandService.handle(new CompleteRouteAssignmentCommand(fleetId, assignmentId))
                .map(FleetResourceFromEntityAssembler::toResource).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
