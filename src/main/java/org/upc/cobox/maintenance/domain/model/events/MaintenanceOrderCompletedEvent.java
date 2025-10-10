package org.upc.cobox.maintenance.domain.model.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaintenanceOrderCompletedEvent(
        Long orderId,
        BigDecimal totalCost,
        String closingOdometer,
        LocalDateTime occurredOn
) {
}