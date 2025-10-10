// src/main/java/org/upc/cobox/incident/application/internal/queryservices/IncidentQueryServiceImpl.java
package org.upc.cobox.incident.application.internal.queryservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upc.cobox.incident.domain.model.aggregates.Incident;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentId;
import org.upc.cobox.incident.infrastructure.persistence.jpa.repositories.IncidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncidentQueryServiceImpl {

    private final IncidentRepository incidentRepository;

    @Transactional(readOnly = true)
    public List<Incident> handleGetAll() {
        return incidentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Incident> handleGetById(IncidentId id) {
        if (id == null) return Optional.empty();
        // 👇 Usar el business id, NO el PK Long
        return incidentRepository.findByIncidentId(id);
    }
}
