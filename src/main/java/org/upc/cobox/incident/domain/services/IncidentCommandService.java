package org.upc.cobox.incident.domain.services;
import org.upc.cobox.incident.domain.model.aggregates.Incident;
import org.upc.cobox.incident.domain.model.commands.AssignResponsibleUserCommand;
import org.upc.cobox.incident.domain.model.commands.CreateIncidentCommand;
import org.upc.cobox.incident.domain.model.commands.UpdateIncidentStatusCommand;

import java.util.Optional;

public interface IncidentCommandService {
    Optional<Incident> handle(CreateIncidentCommand command);
    Optional<Incident> handle(UpdateIncidentStatusCommand command);
    Optional<Incident> handle(AssignResponsibleUserCommand command);
}