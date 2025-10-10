package org.upc.cobox.incident.interfaces.rest.resources;

/**
 * Representa la petición REST para crear una nueva incidencia.
 */
public record CreateIncidentResource(
        String type,
        String description,
        String severity,
        String responsibleUserId
) {}
