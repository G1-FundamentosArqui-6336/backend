package org.upc.cobox.maintenance.interfaces.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import org.upc.cobox.maintenance.domain.services.MaintenanceOrderCommandService;
import org.upc.cobox.maintenance.domain.services.MaintenanceOrderQueryService;
import org.upc.cobox.maintenance.domain.model.aggregates.MaintenanceOrder;
import org.upc.cobox.maintenance.domain.model.queries.*;

import org.upc.cobox.maintenance.interfaces.rest.resources.*;
import org.upc.cobox.maintenance.interfaces.rest.transform.*;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/maintenance-orders")
@RequiredArgsConstructor
@Tag(name = "Maintenance Orders", description = "Create, plan, and manage vehicle maintenance orders")
public class MaintenanceOrderController {

    private final MaintenanceOrderCommandService commandService;
    private final MaintenanceOrderQueryService queryService;

    @Operation(
            summary = "Create a maintenance order",
            description = "Creates a new maintenance order for a vehicle. Optionally schedules it if start/end are provided.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Creation payload",
                    content = @Content(
                            schema = @Schema(implementation = CreateMaintenanceOrderResource.class),
                            examples = @ExampleObject(
                                    name = "Create order",
                                    value = """
                                            {
                                              "vehicleId": 1,
                                              "maintenanceType": "PREDICTIVE",
                                              "priority": "MEDIUM",
                                              "reason": "TIME",
                                              "openingOdometer": 12000,
                                              "startTime": "2025-10-11T09:00:00",
                                              "endTime": "2025-10-11T10:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = MaintenanceOrderResource.class))),
            @ApiResponse(responseCode = "409", description = "Open order already exists for the vehicle")
    })
    @PostMapping
    public ResponseEntity<MaintenanceOrderResource> create(
            @Valid @org.springframework.web.bind.annotation.RequestBody CreateMaintenanceOrderResource body,
            UriComponentsBuilder uriBuilder) {

        var cmd = CreateMaintenanceOrderCommandFromResourceAssembler.toCommand(body);
        var created = commandService.handle(cmd); // Optional<MaintenanceOrder>
        if (created.isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        MaintenanceOrder entity = created.get();
        MaintenanceOrderResource resource = MaintenanceOrderResourceFromEntityAssembler.toResource(entity);

        URI location = uriBuilder
                .path("/api/v1/maintenance-orders/{id}")
                .build(resource.id());
        return ResponseEntity.created(location).body(resource);
    }

    @Operation(
            summary = "Schedule an order",
            description = "Assigns a timeslot to an existing OPEN order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order scheduled"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "409", description = "Time conflict detected")
    })
    @PostMapping("/{id}/schedule")
    public ResponseEntity<Void> schedule(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody ScheduleMaintenanceOrderResource body) {

        var cmd = ScheduleMaintenanceOrderCommandFromResourceAssembler.toCommand(
                new ScheduleMaintenanceOrderResource(id, body.vehicleId(), body.startTime(), body.endTime()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Start an order",
            description = "Transitions a SCHEDULED order to IN_PROGRESS with an assigned technician."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order started"),
            @ApiResponse(responseCode = "400", description = "Invalid state or timeslot missing"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "409", description = "Timeslot overlap")
    })
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> start(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody StartMaintenanceOrderResource body) {

        var cmd = StartMaintenanceOrderCommandFromResourceAssembler.toCommand(
                new StartMaintenanceOrderResource(id, body.vehicleId(), body.technicianId()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Complete an order",
            description = "Marks an IN_PROGRESS order as COMPLETED after validations (jobs done, parts received, odometer >= opening)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order completed"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CompleteMaintenanceOrderResource body) {

        var cmd = CompleteMaintenanceOrderCommandFromResourceAssembler.toCommand(
                new CompleteMaintenanceOrderResource(id, body.closingOdometer()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels an order in any non-completed state."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody CancelMaintenanceOrderResource body) {

        var cmd = CancelMaintenanceOrderCommandFromResourceAssembler.toCommand(
                new CancelMaintenanceOrderResource(id, body.reason()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Register a job",
            description = "Adds a job to an IN_PROGRESS order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job registered"),
            @ApiResponse(responseCode = "400", description = "Order not IN_PROGRESS"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/jobs")
    public ResponseEntity<Void> registerJob(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody RegisterJobResource body) {

        var cmd = RegisterJobCommandFromResourceAssembler.toCommand(
                new RegisterJobResource(id, body.description(), body.estimatedDuration(), body.technicianId(), body.partNumber()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Request parts",
            description = "Requests parts for an order (any state)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parts request registered"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/parts/request")
    public ResponseEntity<Void> requestParts(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody RequestPartsResource body) {

        var cmd = RequestPartsCommandFromResourceAssembler.toCommand(
                new RequestPartsResource(id, body.partNumber(), body.description(), body.quantity(), body.unit(), body.supplierId()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Receive parts",
            description = "Marks a part request as received for the order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parts received"),
            @ApiResponse(responseCode = "404", description = "Order or parts request not found")
    })
    @PostMapping("/{id}/parts/receive")
    public ResponseEntity<Void> receiveParts(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody ReceivePartsResource body) {

        var cmd = ReceivePartsCommandFromResourceAssembler.toCommand(
                new ReceivePartsResource(id, body.partNumber()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Register additional cost",
            description = "Adds extra cost to the order total in the same currency."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cost registered"),
            @ApiResponse(responseCode = "400", description = "Currency mismatch"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/cost")
    public ResponseEntity<Void> registerCost(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody RegisterCostResource body) {

        var cmd = RegisterCostCommandFromResourceAssembler.toCommand(
                new RegisterCostResource(id, body.amount(), body.currency()));
        commandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    // ------------------------------------------------------------
    // Queries
    // ------------------------------------------------------------

    @Operation(
            summary = "Get order by id",
            description = "Retrieves a single maintenance order by its identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = MaintenanceOrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceOrderResource> getById(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id) {

        var query = new GetMaintenanceOrderByIdQuery(id);
        var maybe = queryService.handle(query); // Optional<MaintenanceOrder>
        if (maybe.isEmpty()) return ResponseEntity.notFound().build();

        MaintenanceOrderResource resource = MaintenanceOrderResourceFromEntityAssembler.toResource(maybe.get());
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "List orders by status",
            description = "Returns all orders with the given status.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MaintenanceOrderResource.class)))
            )
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceOrderResource>> getByStatus(
            @Parameter(description = "Status (OPEN|SCHEDULED|IN_PROGRESS|COMPLETED|CANCELLED)", required = true)
            @PathVariable String status) {

        var query = new GetMaintenanceOrdersByStatusQuery(status);
        var list = queryService.handle(query); // List<MaintenanceOrder>
        var resources = list.stream().map(MaintenanceOrderResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(
            summary = "Get open orders for vehicle",
            description = "Returns all OPEN orders for a given vehicle.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "List of open orders",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MaintenanceOrderResource.class)))
            )
    )
    @GetMapping("/vehicle/{vehicleId}/open")
    public ResponseEntity<List<MaintenanceOrderResource>> getOpenByVehicle(
            @Parameter(description = "Vehicle identifier", required = true) @PathVariable Long vehicleId) {

        var query = new GetOpenMaintenanceOrdersByVehicleIdQuery(vehicleId);
        var list = queryService.handle(query);
        var resources = list.stream().map(MaintenanceOrderResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(
            summary = "Has open order for vehicle",
            description = "Checks if the vehicle currently has any OPEN order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boolean result")
    })
    @GetMapping("/vehicle/{vehicleId}/has-open")
    public ResponseEntity<Boolean> hasOpenForVehicle(
            @Parameter(description = "Vehicle identifier", required = true) @PathVariable Long vehicleId) {

        var query = new HasMaintenanceOpenOrderForVehicleIdQuery(vehicleId);
        boolean result = queryService.handle(query);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get order history for vehicle",
            description = "Returns all orders (any status) belonging to the vehicle, sorted chronologically.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MaintenanceOrderResource.class)))
            )
    )
    @GetMapping("/vehicle/{vehicleId}/history")
    public ResponseEntity<List<MaintenanceOrderResource>> getHistory(
            @Parameter(description = "Vehicle identifier", required = true) @PathVariable Long vehicleId) {

        var query = new GetMaintenanceOrderHistoryQuery(vehicleId);
        var list = queryService.handle(query);
        var resources = list.stream().map(MaintenanceOrderResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(resources);
    }
}
