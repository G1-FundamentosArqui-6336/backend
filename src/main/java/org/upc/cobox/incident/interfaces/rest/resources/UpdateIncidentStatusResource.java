package org.upc.cobox.incident.interfaces.rest.resources;

/**
 * Representa la petición para actualizar el estado de una incidencia.
 */
public record UpdateIncidentStatusResource(
        String nextStatus
) {}
