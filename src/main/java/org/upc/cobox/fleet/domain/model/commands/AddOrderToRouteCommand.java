package org.upc.cobox.fleet.domain.model.commands;

public record AddOrderToRouteCommand (Long routeId, Long orderId) {
}
