package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record PartsRequestedEvent(
        Long orderId,
        String partNumber,
        Integer quantity,
        LocalDateTime occurredOn
) {
}
