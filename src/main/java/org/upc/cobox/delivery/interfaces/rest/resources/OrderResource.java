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
        Double referenceLatitude,
        Double referenceLongitude,
        String notes,
        Double weightKg,
        String orderStatus
){
}