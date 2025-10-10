package org.upc.cobox.maintenance.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceSchedule;
import org.upc.cobox.maintenance.domain.model.queries.GetMaintenanceScheduleByIdQuery;
import org.upc.cobox.maintenance.domain.services.MaintenanceScheduleCommandService;
import org.upc.cobox.maintenance.domain.services.MaintenanceScheduleQueryService;

import org.upc.cobox.maintenance.interfaces.rest.resources.*;
import org.upc.cobox.maintenance.interfaces.rest.transform.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maintenance-schedules")
@RequiredArgsConstructor
@Tag(name = "Maintenance Schedules", description = "Define, activate and evaluate maintenance schedules for vehicles")
public class MaintenanceScheduleController {

    private final MaintenanceScheduleCommandService commandService;
    private final MaintenanceScheduleQueryService queryService; // only used for GET by id

    // ------------------- Create -------------------

    @Operation(
            summary = "Create a maintenance schedule",
            description = "Creates a schedule for a given vehicle with its maintenance rules.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Creation payload",
                    content = @Content(
                            schema = @Schema(implementation = CreateMaintenanceScheduleResource.class),
                            examples = @ExampleObject(
                                    name = "Create schedule",
                                    value = """
                                            {
                                              "vehicleId": 42,
                                              "rules": [
                                                {"criteria": "MILEAGE", "everyMonths": null, "everyKilometers": 10000, "tolerancePercentage": 10, "maintenanceType": "PREVENTIVE", "defaultPriority": "MEDIUM"},
                                                {"criteria": "TIME", "everyMonths": 6, "everyKilometers": null, "tolerancePercentage": 10, "maintenanceType": "PREVENTIVE", "defaultPriority": "MEDIUM"}
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Schedule created", content = @Content(schema = @Schema(implementation = MaintenanceScheduleResource.class))),
            @ApiResponse(responseCode = "409", description = "Schedule already exists for the vehicle/type")
    })
    @PostMapping
    public ResponseEntity<MaintenanceScheduleResource> create(
            @Valid @RequestBody CreateMaintenanceScheduleResource body,
            UriComponentsBuilder uriBuilder) {

        var cmd = CreateMaintenanceScheduleCommandFromResourceAssembler.toCommand(body);
        Optional<MaintenanceSchedule> created = commandService.handle(cmd);
        if (created.isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        var resource = MaintenanceScheduleResourceFromEntityAssembler.toResource(created.get());
        URI location = uriBuilder.path("/api/v1/maintenance-schedules/{id}").build(resource.id());
        return ResponseEntity.created(location).body(resource);
    }

    // ------------------- Activate -------------------

    @Operation(
            summary = "Activate a schedule",
            description = "Marks the schedule as ACTIVE and ready to be evaluated and produce orders."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule activated"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(
            @Parameter(description = "Schedule ID", required = true) @PathVariable Long id,
            @Valid @RequestBody ActivateMaintenanceScheduleResource body) {

        var cmd = ActivateMaintenanceScheduleCommandFromResourceAssembler.toCommand(
                new ActivateMaintenanceScheduleResource(id));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    // ------------------- Deactivate -------------------

    @Operation(
            summary = "Deactivate a schedule",
            description = "Marks the schedule as INACTIVE; it won’t trigger evaluations until reactivated."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule deactivated"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @Parameter(description = "Schedule ID", required = true) @PathVariable Long id,
            @Valid @RequestBody DeactivateMaintenanceScheduleResource body) {

        var cmd = DeactivateMaintenanceScheduleCommandFromResourceAssembler.toCommand(
                new DeactivateMaintenanceScheduleResource(id));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    // ------------------- Evaluate -------------------

    @Operation(
            summary = "Evaluate a schedule",
            description = "Evaluates the schedule against current telemetry (e.g., odometer) and time. May produce due/overdue status and emit domain events.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Evaluation payload",
                    content = @Content(
                            schema = @Schema(implementation = EvaluateMaintenanceScheduleResource.class),
                            examples = @ExampleObject(
                                    name = "Evaluate schedule",
                                    value = """
                                            {
                                              "scheduleId": 15,
                                              "currentKm": 20500,
                                              "evaluationTime": "2025-10-09T16:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule evaluated"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "409", description = "Schedule inactive")
    })
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<Void> evaluate(
            @Parameter(description = "Schedule ID", required = true) @PathVariable Long id,
            @Valid @RequestBody EvaluateMaintenanceScheduleResource body) {

        var cmd = EvaluateMaintenanceScheduleCommandFromResourceAssembler.toCommand(
                new EvaluateMaintenanceScheduleResource(id, body.currentKm(), body.evaluationTime()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    // ------------------- Update Rules -------------------

    @Operation(
            summary = "Update schedule rules",
            description = "Replaces the current set of maintenance rules with the provided list."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rules updated"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    @PutMapping("/{id}/rules")
    public ResponseEntity<Void> updateRules(
            @Parameter(description = "Schedule ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UpdateMaintenanceRulesResource body) {

        var cmd = UpdateMaintenanceRulesCommandFromResourceAssembler.toCommand(
                new UpdateMaintenanceRulesResource(id, body.rules()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    // ------------------- Get by id (no query DTOs) -------------------

    @Operation(
            summary = "Get schedule by id",
            description = "Retrieves a single maintenance schedule by its identifier.",
            responses = @ApiResponse(responseCode = "200", description = "Schedule found",
                    content = @Content(schema = @Schema(implementation = MaintenanceScheduleResource.class)))
    )
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceScheduleResource> getById(
            @Parameter(description = "Schedule ID", required = true) @PathVariable Long id) {

        return queryService.handle(new GetMaintenanceScheduleByIdQuery(id))
                .map(MaintenanceScheduleResourceFromEntityAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
