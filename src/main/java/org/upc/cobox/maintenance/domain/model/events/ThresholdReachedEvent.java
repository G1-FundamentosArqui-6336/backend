package org.upc.cobox.maintenance.domain.model.events;

import java.time.LocalDateTime;

public record ThresholdReachedEvent(
        Long scheduleId,
        Long vehicleId,
        String maintenanceType,
        String reason,
        Integer currentOdometer,
        LocalDateTime occurredOn
) {
}