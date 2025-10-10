package org.upc.cobox.incident.interfaces.rest.transform;

import org.upc.cobox.incident.domain.model.commands.AssignResponsibleUserCommand;
import org.upc.cobox.incident.domain.model.valueobjects.IncidentId;
import org.upc.cobox.incident.domain.model.valueobjects.ResponsibleUserId;
import org.upc.cobox.incident.interfaces.rest.resources.AssignResponsibleUserResource;

import java.util.UUID;

public final class AssignResponsibleUserCommandFromResourceAssembler {

    private AssignResponsibleUserCommandFromResourceAssembler() {}

    public static AssignResponsibleUserCommand toCommandFromResource(String id, AssignResponsibleUserResource r) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("incident id es obligatorio");
        if (r == null) throw new IllegalArgumentException("AssignResponsibleUserResource no puede ser nulo");

        var incidentId = new IncidentId(parseUuid(id, "id"));
        var responsible = new ResponsibleUserId(parseUuid(req(r.userId(), "userId"), "userId"));

        return new AssignResponsibleUserCommand(incidentId, responsible);
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
