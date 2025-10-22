package org.upc.cobox.fleet.domain.model.commands;

public record AddDeliveredOrderToRouteCommand (Long routeId, Long orderId) {
}
