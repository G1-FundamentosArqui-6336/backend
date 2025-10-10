package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record MaintenanceOrderScheduledEvent(
        Long orderId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime occurredOn
) {
}