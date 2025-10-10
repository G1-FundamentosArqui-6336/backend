// src/main/java/org/upc/cobox/incident/application/internal/commandservices/IncidentCommandServiceImpl.java
package org.upc.cobox.incident.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.upc.cobox.incident.domain.model.aggregates.Incident;
import org.upc.cobox.incident.domain.model.commands.AssignResponsibleUserCommand;
import org.upc.cobox.incident.domain.model.commands.CreateIncidentCommand;
import org.upc.cobox.incident.domain.model.commands.UpdateIncidentStatusCommand;
import org.upc.cobox.incident.domain.services.IncidentCommandService;
import org.upc.cobox.incident.infrastructure.persistence.jpa.repositories.IncidentRepository;

import java.util.Optional;

@Service
@Transactional
public class IncidentCommandServiceImpl implements IncidentCommandService {

    private final IncidentRepository incidentRepository;

    public IncidentCommandServiceImpl(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public Optional<Incident> handle(CreateIncidentCommand command) {
        Incident incident = new Incident(command);
        return Optional.of(incidentRepository.save(incident));
    }

    @Override
    public Optional<Incident> handle(UpdateIncidentStatusCommand command) {
        return incidentRepository.findByIncidentId(command.incidentId())
                .flatMap(existing -> {
                    boolean ok = existing.updateStatus(command);
                    if (!ok) return Optional.empty();
                    return Optional.of(incidentRepository.save(existing));
                });
    }

    @Override
    public Optional<Incident> handle(AssignResponsibleUserCommand command) {
        return incidentRepository.findByIncidentId(command.incidentId())
                .flatMap(existing -> {
                    boolean ok = existing.assignResponsible(command);
                    if (!ok) return Optional.empty();
                    return Optional.of(incidentRepository.save(existing));
                });
    }
}
