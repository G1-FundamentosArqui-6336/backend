
package org.upc.cobox.incident.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String value) {
    public Description { if (value == null || value.isBlank()) throw new IllegalArgumentException("..."); }
}

