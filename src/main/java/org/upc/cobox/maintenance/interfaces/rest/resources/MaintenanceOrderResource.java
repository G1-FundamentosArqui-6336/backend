package org.upc.cobox.maintenance.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record MaintenanceOrderResource(
        Long id,
        Long vehicleId,
        String maintenanceType,
        String priority,
        String status,
        String reason,
        Integer openingKm,
        Integer closingKm,
        LocalDateTime scheduledStart,
        LocalDateTime scheduledEnd,
        BigDecimal totalCost,
        String currency,
        Long technicianId,
        List<JobResource> jobs,
        List<PartsRequestResource> partsRequests
) {}
