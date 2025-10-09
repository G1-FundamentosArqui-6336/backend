package org.upc.cobox.delivery.interfaces.rest;



import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.queries.GetAllOrders;
import org.upc.cobox.delivery.domain.model.queries.GetOrderByIdAndClientId;
import org.upc.cobox.delivery.domain.model.queries.GetOrdersByClientIdAndStatus;
import org.upc.cobox.delivery.domain.model.queries.GetOrdersByStatus;
import org.upc.cobox.delivery.domain.services.OrderCommandService;
import org.upc.cobox.delivery.domain.services.OrderQueryService;
import org.upc.cobox.delivery.interfaces.rest.resources.*;
import org.upc.cobox.delivery.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Orders")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrderController(
            OrderCommandService orderCommandService,
            OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }


    // GET /api/v1/orders
    @Operation(summary = "List orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResource.class))))
    })
    @GetMapping
    public ResponseEntity<List<OrderResource>> listOrders() {
        var query=new GetAllOrders();
        var orders = orderQueryService.handle(query);
        var resources = orders.stream().map(OrderResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }



    // GET /api/v1/orders?status=IN_TRANSIT
    @Operation(summary = "List orders (optional filter by status)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResource.class))))
    })
    @GetMapping(params = "status")
    public ResponseEntity<List<OrderResource>> listOrdersByStatus(
            @RequestParam(name = "status", required = false) String status) {

        var query=new GetOrdersByStatus(status);
        var orders = orderQueryService.handle(query);
        var resources = orders.stream().map(OrderResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }


    // POST /api/v1/orders/{orderId}/validate-delivery
    @Operation(summary = "Validate delivery with evidence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery validated",
                    content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid evidence or state", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PostMapping(value = "/{orderId}/validate-delivery", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResource> validateDelivery(
            @PathVariable Long orderId,
            @RequestBody ValidateDeliveryResource resource) {

        Optional<Order> validated = orderCommandService.handle(
                ValidateDeliveryCommandFromResourceAssembler.toCommandFromResource( orderId, resource));

        return validated
                .map(o -> ResponseEntity.ok(OrderResourceFromEntityAssembler.toResourceFromEntity(o)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @PostMapping(value = "/{orderId}/assign-vehicle", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResource> assignVehicle(
            @PathVariable Long orderId,
            @RequestBody AssignVehicleResource resource) {

        var result = orderCommandService.handle(
                AssignVehicleCommandFromResourceAssembler.toCommand(orderId, resource));

        return result
                .map(o -> ResponseEntity.ok(OrderResourceFromEntityAssembler.toResourceFromEntity(o)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
