package org.upc.cobox.delivery.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record CreateOrderResource(
        String addressLine,
        String city,
        String country,
        String postalCode,
        String reference,
        Date scheduledAt,
        String notes,
        BigDecimal totalWeight
) {}