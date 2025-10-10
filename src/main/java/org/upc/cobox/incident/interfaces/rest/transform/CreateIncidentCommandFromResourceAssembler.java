package org.upc.cobox.incident.interfaces.rest.transform;

import org.upc.cobox.incident.domain.model.commands.CreateIncidentCommand;
import org.upc.cobox.incident.domain.model.valueobjects.*;
import org.upc.cobox.incident.interfaces.rest.resources.CreateIncidentResource;

import java.util.Locale;
import java.util.UUID;

public final class CreateIncidentCommandFromResourceAssembler {

    private CreateIncidentCommandFromResourceAssembler() {}

    public static CreateIncidentCommand toCommandFromResource(CreateIncidentResource r) {
        if (r == null) throw new IllegalArgumentException("CreateIncidentResource no puede ser nulo");

        var type = new IncidentType(req(r.type(), "type"));
        var description = new Description(req(r.description(), "description"));

        // Normaliza a enum
        var severity = Severity.valueOf(
                req(r.severity(), "severity").trim().toUpperCase(Locale.ROOT)
        );

        // responsibleUserId es opcional
        ResponsibleUserId responsible = null;
        if (r.responsibleUserId() != null && !r.responsibleUserId().isBlank()) {
            responsible = new ResponsibleUserId(parseUuid(r.responsibleUserId(), "responsibleUserId"));
        }

        return new CreateIncidentCommand(type, description, severity, responsible);
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
