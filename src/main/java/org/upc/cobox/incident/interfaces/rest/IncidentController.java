// src/main/java/org/upc/cobox/incident/interfaces/rest/IncidentController.java
package org.upc.cobox.incident.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upc.cobox.incident.application.internal.commandservices.IncidentCommandServiceImpl;
import org.upc.cobox.incident.application.internal.queryservices.IncidentQueryServiceImpl;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentId;
import org.upc.cobox.incident.interfaces.rest.resources.*;
import org.upc.cobox.incident.interfaces.rest.transform.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentCommandServiceImpl commandService;
    private final IncidentQueryServiceImpl queryService;

    // ---------- Commands ----------

    @PostMapping
    public ResponseEntity<IncidentResource> create(@RequestBody CreateIncidentResource body) {
        var cmd = CreateIncidentCommandFromResourceAssembler.toCommandFromResource(body);
        var createdOpt = commandService.handle(cmd);

        return createdOpt
                .map(inc -> {
                    var resource = IncidentResourceFromEntityAssembler.toResourceFromEntity(inc);
                    return ResponseEntity
                            .created(URI.create("/api/v1/incidents/" + resource.id()))
                            .body(resource);
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable String id,
                                             @RequestBody UpdateIncidentStatusResource body) {
        var cmd = UpdateIncidentStatusCommandFromResourceAssembler.toCommandFromResource(id, body);
        var updatedOpt = commandService.handle(cmd);
        return updatedOpt.isPresent() ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Void> assignResponsible(@PathVariable String id,
                                                  @RequestBody AssignResponsibleUserResource body) {
        var cmd = AssignResponsibleUserCommandFromResourceAssembler.toCommandFromResource(id, body);
        var updatedOpt = commandService.handle(cmd);
        return updatedOpt.isPresent() ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }

    // ---------- Queries ----------

    @GetMapping
    public ResponseEntity<?> getAll() {
        var list = IncidentResourceFromEntityAssembler
                .toResourceListFromEntities(queryService.handleGetAll());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResource> getById(@PathVariable String id) {
        var vo = new IncidentId(UUID.fromString(id));
        return queryService.handleGetById(vo)
                .map(IncidentResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
