// src/main/java/org/upc/cobox/incident/infrastructure/persistence/jpa/repositories/IncidentRepository.java
        package org.upc.cobox.incident.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upc.cobox.incident.domain.model.aggregates.Incident;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentId;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentStatus;
import org.upc.cobox.incident.domain.model.valueobjects.ResponsibleUserId;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Optional<Incident> findByIncidentId(IncidentId incidentId);
    List<Incident> findByStatus(IncidentStatus status);
    List<Incident> findByResponsibleUserId(ResponsibleUserId userId);
}
