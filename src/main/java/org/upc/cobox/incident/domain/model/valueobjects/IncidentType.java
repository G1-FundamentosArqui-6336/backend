
package org.upc.cobox.incident.domain.model.valueobjects;

public record IncidentType(String value) {
    public IncidentType {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Incident type is required");
    }
}
