package org.upc.cobox.incident.domain.model.valueobjects;

import java.util.UUID;

public record ResponsibleUserId(UUID value) {
    public ResponsibleUserId {
        if (value == null) throw new IllegalArgumentException("ResponsibleUserId cannot be null");
    }
}
