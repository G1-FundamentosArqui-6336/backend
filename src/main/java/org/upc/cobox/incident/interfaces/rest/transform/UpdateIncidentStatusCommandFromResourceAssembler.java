package org.upc.cobox.incident.interfaces.rest.transform;

import org.upc.cobox.incident.domain.model.commands.UpdateIncidentStatusCommand;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentId;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentStatus;
import org.upc.cobox.incident.interfaces.rest.resources.UpdateIncidentStatusResource;

import java.util.Locale;
import java.util.UUID;

public final class UpdateIncidentStatusCommandFromResourceAssembler {

    private UpdateIncidentStatusCommandFromResourceAssembler() {}

    public static UpdateIncidentStatusCommand toCommandFromResource(String id, UpdateIncidentStatusResource r) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("incident id es obligatorio");
        if (r == null) throw new IllegalArgumentException("UpdateIncidentStatusResource no puede ser nulo");

        var incidentId = new IncidentId(parseUuid(id, "id"));

        var newStatus = IncidentStatus.valueOf(
                req(r.nextStatus(), "nextStatus").trim().toUpperCase(Locale.ROOT)
        );

        return new UpdateIncidentStatusCommand(incidentId, newStatus);
    }

    private static String req(String v, String field) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(field + " es obligatorio");
        return v;
    }

    private static UUID parseUuid(String v, String field) {
        try { return UUID.fromString(v.trim()); }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(field + " no es un UUID válido: " + v);
        }
    }
}
