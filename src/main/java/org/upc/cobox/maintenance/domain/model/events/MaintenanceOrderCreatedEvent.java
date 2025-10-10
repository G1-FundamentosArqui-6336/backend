package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record MaintenanceOrderCreatedEvent(
        Long orderId,
        Long vehicleId,
        String maintenanceType,
        String reason,
        String priority,
        LocalDateTime occurredOn
) {
}