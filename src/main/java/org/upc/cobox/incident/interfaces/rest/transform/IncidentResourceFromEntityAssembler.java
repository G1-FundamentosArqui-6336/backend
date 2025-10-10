package org.upc.cobox.incident.interfaces.rest.transform;

import org.upc.cobox.incident.domain.model.aggregates.Incident;
import org.upc.cobox.incident.domain.model.valueobjects.ResponsibleUserId;
import org.upc.cobox.incident.interfaces.rest.resources.IncidentResource;

import java.util.List;
import java.util.Objects;

public final class IncidentResourceFromEntityAssembler {

    private IncidentResourceFromEntityAssembler() {}

    public static IncidentResource toResourceFromEntity(Incident i) {
        Objects.requireNonNull(i, "Incident no puede ser nulo");

        // id es Long (no value object)
        String id = i.getId() != null ? i.getId().toString() : null;

        // responsibleUserId puede ser null; es un VO si existe
        String responsible =
                i.getResponsibleUserId() != null
                        ? safeUuid(i.getResponsibleUserId())
                        : null;

        return new IncidentResource(
                id,
                i.getType().value(),
                i.getDescription().value(),
                i.getReportedAt().value().toString(),
                i.getSeverity().name(),
                i.getStatus().name(),
                responsible
        );
    }

    public static List<IncidentResource> toResourceListFromEntities(List<Incident> incidents) {
        return incidents == null
                ? List.of()
                : incidents.stream()
                .map(IncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    private static String safeUuid(ResponsibleUserId vo) {
        return vo.value() != null ? vo.value().toString() : null;
    }
}
