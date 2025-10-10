package org.upc.cobox.delivery.interfaces.rest.transform;


import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.CreateOrderResource;

import java.math.BigDecimal;
import java.util.Date;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommandFromResource(Long clientId, CreateOrderResource r) {
        return new CreateOrderCommand(
                clientId,
                r.addressLine(),
                r.city(),
                r.country(),
                r.postalCode(),
                r.reference(),
                safeDate(r.scheduledAt()),
                r.notes(),
                safeWeight(r.totalWeight())
        );
    }

    private static Date safeDate(Date d) { return d == null ? new Date() : d; }
    private static BigDecimal safeWeight(BigDecimal w) { return w == null ? BigDecimal.ZERO : w; }
}