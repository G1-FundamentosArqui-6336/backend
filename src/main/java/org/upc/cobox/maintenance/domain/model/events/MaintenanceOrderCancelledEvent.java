package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record MaintenanceOrderCancelledEvent(
        Long orderId,
        String reason,
        LocalDateTime occurredOn
) {
}