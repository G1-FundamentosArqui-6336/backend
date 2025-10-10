
package org.upc.cobox.incident.domain.model.valueobjects;

public enum IncidentStatus {
    OPEN, IN_PROGRESS, ESCALATED, RESOLVED, CLOSED;

    public boolean canTransitionTo(IncidentStatus next) {
        return switch (this) {
            case OPEN -> next == IN_PROGRESS || next == ESCALATED || next == CLOSED;
            case IN_PROGRESS -> next == ESCALATED || next == RESOLVED || next == CLOSED;
            case ESCALATED -> next == IN_PROGRESS || next == RESOLVED || next == CLOSED;
            case RESOLVED -> next == CLOSED;
            case CLOSED -> false;
        };
    }
}
