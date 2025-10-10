package org.upc.cobox.maintenance.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record MaintenanceScheduleResource(
        Long id,
        Long vehicleId,
        String status,                 // "ACTIVE" | "INACTIVE"
        LocalDateTime lastEvaluationAt,
        LocalDateTime nextEvaluationAt,
        List<MaintenanceRuleResource> rules
) {}
