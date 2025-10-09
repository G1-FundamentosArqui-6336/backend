package org.upc.cobox.delivery.interfaces.rest.resources;

import java.util.Date;

public record ValidateDeliveryResource(
        String receiverName,
        String photoUrl,
        String signatureCode,
        Date takenAt
) {}