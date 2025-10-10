// src/main/java/org/upc/cobox/incident/domain/model/events/IncidentReportedEvent.java
package org.upc.cobox.incident.domain.model.events;

import org.upc.cobox.incident.domain.model.valueobjects.*;
import java.time.Instant;

/** Se emite cuando se crea una incidencia. */
public record IncidentReportedEvent(
        Object aggregateId,           // el tipo de ID que hereda tu AuditableAbstractAggregateRoot
        IncidentType type,
        Severity severity,
        ReportedAt reportedAt
) {
    public Instant occurredOn() { return reportedAt.value(); }
}
