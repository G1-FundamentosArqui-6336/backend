package org.upc.cobox.incident.interfaces.rest.resources;

/**
 * Representa la respuesta REST que describe una incidencia.
 */
public record IncidentResource(
        String id,
        String type,
        String description,
        String reportedAt,
        String severity,
        String status,
        String responsibleUserId
) {}
