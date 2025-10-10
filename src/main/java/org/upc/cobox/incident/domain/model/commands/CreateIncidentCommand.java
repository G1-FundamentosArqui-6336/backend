// src/main/java/org/upc/cobox/incident/domain/model/commands/CreateIncidentCommand.java
package org.upc.cobox.incident.domain.model.commands;

import org.upc.cobox.incident.domain.model.valueobjects.*;

public record CreateIncidentCommand(
        IncidentType type,
        Description description,
        Severity severity,
        ResponsibleUserId responsibleUserId
) {}