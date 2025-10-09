package org.upc.cobox.incident.domain.model.commands;

import org.upc.cobox.incident.domain.model.valueobjects.*;

public record AssignResponsibleUserCommand(
        IncidentId incidentId,
        ResponsibleUserId newResponsibleUserId
) {}