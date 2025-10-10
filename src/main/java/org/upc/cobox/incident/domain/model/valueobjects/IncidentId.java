
package org.upc.cobox.incident.domain.model.valueobjects;

import java.util.UUID;

public record IncidentId(UUID value) {
    public IncidentId {
        if (value == null) throw new IllegalArgumentException("IncidentId cannot be null");
    }
    public static IncidentId newId() { return new IncidentId(UUID.randomUUID()); }
}
