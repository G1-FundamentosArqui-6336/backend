package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record PartsReceivedEvent(
        Long orderId,
        String partNumber,
        LocalDateTime occurredOn
) {
}
