package org.upc.cobox.delivery.domain.model.commands;

public record MarkAsCompletedOrderCommand(Long orderId, Long evidenceId, Long routeId) {
}
