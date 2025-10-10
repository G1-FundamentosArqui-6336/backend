package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record MaintenanceOrderStartedEvent(
        Long orderId,
        Long technicianId,
        LocalDateTime occurredOn
) {
}