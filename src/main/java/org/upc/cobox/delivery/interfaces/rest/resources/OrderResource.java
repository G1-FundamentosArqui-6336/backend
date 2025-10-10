package org.upc.cobox.delivery.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record OrderResource(
        Long id,
        Long clientId,
        String addressLine,
        String city,
        String country,
        String postalCode,
        String reference,
        Date scheduledAt,
        Date deliveredAt,
        String status,
        String notes,
        BigDecimal totalWeight,
        EvidenceResource evidence
){
    public record EvidenceResource(
            String receiverName,
            String photoUrl,
            String signatureCode,
            Date takenAt
    ) {}
}