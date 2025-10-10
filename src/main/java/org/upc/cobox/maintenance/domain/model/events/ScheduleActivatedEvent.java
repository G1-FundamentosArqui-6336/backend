package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record ScheduleActivatedEvent(
        Long scheduleId,
        Long vehicleId,
        LocalDateTime occurredOn
) {
}