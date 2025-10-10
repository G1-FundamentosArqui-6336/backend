package org.upc.cobox.maintenance.interfaces.rest.resources;

public record JobResource(
        Long id,
        String description,
        Integer estimatedDuration,
        boolean completed,
        Long technicianId,
        String partNumber
) {}
