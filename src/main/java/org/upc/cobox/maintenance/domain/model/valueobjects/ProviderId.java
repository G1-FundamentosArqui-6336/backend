package org.upc.cobox.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProviderId(Long providerId) {
    public ProviderId {
        if (providerId < 0) {
            throw new IllegalArgumentException("ProviderId cannot be negative");
        }
    }
    public ProviderId() { this(0L); }
}