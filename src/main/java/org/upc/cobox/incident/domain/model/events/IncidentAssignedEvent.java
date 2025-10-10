// src/main/java/org/upc/cobox/incident/domain/model/events/IncidentAssignedEvent.java
package org.upc.cobox.incident.domain.model.events;

import org.upc.cobox.incident.domain.model.valueobjects.ResponsibleUserId;

import java.time.Instant;

/** Se emite cuando se asigna un responsable a la incidencia. */
public record IncidentAssignedEvent(
        Object aggregateId,
        ResponsibleUserId to,
        Instant at
) {}
