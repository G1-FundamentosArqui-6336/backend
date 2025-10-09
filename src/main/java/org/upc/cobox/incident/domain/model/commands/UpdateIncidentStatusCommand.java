package org.upc.cobox.incident.domain.model.commands;

import org.upc.cobox.incident.domain.model.valueobjects.*;

public record UpdateIncidentStatusCommand(
        IncidentId incidentId,
        IncidentStatus newStatus
) {}