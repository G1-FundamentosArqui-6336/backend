
package org.upc.cobox.incident.domain.model.valueobjects;

import java.time.Instant;

public record ReportedAt(Instant value) {
    public ReportedAt {
        if (value == null) throw new IllegalArgumentException("ReportedAt is required");
    }
    public static ReportedAt now() { return new ReportedAt(Instant.now()); }
}
