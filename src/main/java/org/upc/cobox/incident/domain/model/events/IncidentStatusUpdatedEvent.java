// src/main/java/org/upc/cobox/incident/domain/model/events/IncidentStatusUpdatedEvent.java
package org.upc.cobox.incident.domain.model.events;

import org.upc.cobox.incident.domain.model.valueobjects.IncidentStatus;

import java.time.Instant;

/** Se emite cuando cambia el estado de la incidencia. */
public record IncidentStatusUpdatedEvent(
        Object aggregateId,
        IncidentStatus from,
        IncidentStatus to,
        Instant at
) {}
