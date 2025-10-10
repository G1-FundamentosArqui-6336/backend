package org.upc.cobox.delivery.domain.model.commands;

import org.upc.cobox.delivery.domain.model.valueobjects.DeliveryStatus;

public record UpdateOrderStatusCommand(Long clientId,Long orderId, DeliveryStatus newStatus) {
}
