package org.upc.cobox.incident.interfaces.rest.resources;

/**
 * Representa la petición para asignar un responsable a una incidencia.
 */
public record AssignResponsibleUserResource(
        String userId
) {}
