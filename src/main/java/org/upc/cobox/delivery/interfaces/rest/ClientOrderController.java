package org.upc.cobox.delivery.interfaces.rest;



import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.domain.model.queries.GetOrderByIdAndClientId;
import org.upc.cobox.delivery.domain.model.queries.GetOrdersByClientIdAndStatus;
import org.upc.cobox.delivery.domain.services.OrderCommandService;
import org.upc.cobox.delivery.domain.services.OrderQueryService;
import org.upc.cobox.delivery.interfaces.rest.resources.CreateOrderResource;
import org.upc.cobox.delivery.interfaces.rest.resources.OrderResource;
import org.upc.cobox.delivery.interfaces.rest.resources.UpdateOrderStatusResource;
import org.upc.cobox.delivery.interfaces.rest.resources.ValidateDeliveryResource;
import org.upc.cobox.delivery.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import org.upc.cobox.delivery.interfaces.rest.transform.OrderResourceFromEntityAssembler;
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
import org.upc.cobox.delivery.interfaces.rest.transform.UpdateOrderStatusCommandFromResourceAssembler;
import org.upc.cobox.delivery.interfaces.rest.transform.ValidateDeliveryCommandFromResourceAssembler;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/clients/{clientId}/orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Client Orders ")
public class ClientOrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public ClientOrderController(
            OrderCommandService orderCommandService,
            OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
    }

    // POST /api/v1/clients/{clientId}/orders
    @Operation(summary = "Create an order for a client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payload", content = @Content)
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResource> createOrderByClient(
            @Parameter(description = "Client identifier") @PathVariable Long clientId,
            @RequestBody CreateOrderResource resource) {

        Optional<Order> order = orderCommandService.handle(
                CreateOrderCommandFromResourceAssembler.toCommandFromResource(clientId, resource));

        return order
                .map(o -> new ResponseEntity<>(OrderResourceFromEntityAssembler.toResourceFromEntity(o), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // GET /api/v1/clients/{clientId}/orders/{orderId}
    @Operation(summary = "Get an order by id and client id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrderByIdAndClientId(@PathVariable Long orderId,@PathVariable Long clientId) {

        var query = new GetOrderByIdAndClientId(orderId,clientId);
        return orderQueryService.handle(query)
                .map(o -> ResponseEntity.ok(OrderResourceFromEntityAssembler.toResourceFromEntity(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /api/v1/clients/{clientId}/orders?status=IN_TRANSIT
    @Operation(summary = "List client orders (optional filter by status)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResource.class))))
    })
    @GetMapping(params = "status")
    public ResponseEntity<List<OrderResource>> listOrdersByClientAndStatus(
            @PathVariable Long clientId,
            @RequestParam(name = "status", required = false) String status) {

        var query=new GetOrdersByClientIdAndStatus(clientId,status);
        var orders = orderQueryService.handle(query);
        var resources = orders.stream().map(OrderResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    // PATCH /api/v1/clients/{clientId}/orders/{orderId}/status
    @Operation(summary = "Update order status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = @Content(schema = @Schema(implementation = OrderResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid transition", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PatchMapping(value = "/{orderId}/status", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResource> updateStatusByClient(
            @PathVariable Long clientId,
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusResource resource) {
        Optional<Order> updated = orderCommandService.handle(
                UpdateOrderStatusCommandFromResourceAssembler.toCommandFromResource(clientId,orderId, resource));
        return updated
                .map(o -> ResponseEntity.ok(OrderResourceFromEntityAssembler.toResourceFromEntity(o)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
