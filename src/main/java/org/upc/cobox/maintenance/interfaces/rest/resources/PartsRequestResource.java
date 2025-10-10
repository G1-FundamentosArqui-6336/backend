package org.upc.cobox.maintenance.interfaces.rest.resources;

public record PartsRequestResource(
        Long id,
        String partNumber,
        String description,
        Integer quantity,
        String unit,
        boolean received,
        Long supplierId
) {}
