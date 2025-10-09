// src/main/java/org/upc/cobox/incident/domain/model/aggregates/Incident.java
package org.upc.cobox.incident.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.upc.cobox.incident.domain.model.commands.AssignResponsibleUserCommand;
import org.upc.cobox.incident.domain.model.commands.CreateIncidentCommand;
import org.upc.cobox.incident.domain.model.commands.UpdateIncidentStatusCommand;
import org.upc.cobox.incident.domain.model.events.IncidentAssignedEvent;
import org.upc.cobox.incident.domain.model.events.IncidentReportedEvent;
import org.upc.cobox.incident.domain.model.events.IncidentStatusUpdatedEvent;
import org.upc.cobox.incident.domain.model.valueobjects.*;
import org.upc.cobox.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Table(name = "incidents")
public class Incident extends AuditableAbstractAggregateRoot<Incident> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Business ID
    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "incident_uuid", nullable = false, updatable = false, unique = true, length = 36))
    private IncidentId incidentId;

    // ---- Embeddeds con columna propia (evita choque 'value') ----
    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "type", nullable = false, length = 50))
    private IncidentType type;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "description", nullable = false, length = 1000))
    private Description description;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "reported_at", nullable = false))
    private ReportedAt reportedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private IncidentStatus status;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "responsible_user_id", length = 36))
    private ResponsibleUserId responsibleUserId;

    @Version
    private Long version;

    protected Incident() {}

    public Incident(CreateIncidentCommand command) {
        this.incidentId   = IncidentId.newId();
        this.type         = command.type();
        this.description  = command.description();
        this.severity     = command.severity();
        this.reportedAt   = ReportedAt.now();
        this.status       = (this.severity == Severity.CRITICAL)
                ? IncidentStatus.ESCALATED
                : IncidentStatus.OPEN;
        if (command.responsibleUserId() != null) this.responsibleUserId = command.responsibleUserId();

        registerEvent(new IncidentReportedEvent(this.incidentId, this.type, this.severity, this.reportedAt));
    }

    public boolean updateStatus(UpdateIncidentStatusCommand command) {
        var next = command.newStatus();
        if (!this.status.canTransitionTo(next)) return false;
        var previous = this.status;
        this.status = next;
        registerEvent(new IncidentStatusUpdatedEvent(this.incidentId, previous, next, Instant.now()));
        return true;
    }

    public boolean assignResponsible(AssignResponsibleUserCommand command) {
        if (this.responsibleUserId != null && this.responsibleUserId.equals(command.newResponsibleUserId()))
            return true;
        this.responsibleUserId = command.newResponsibleUserId();
        registerEvent(new IncidentAssignedEvent(this.incidentId, this.responsibleUserId, Instant.now()));
        return true;
    }
}
