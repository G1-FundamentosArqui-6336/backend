package org.upc.cobox.delivery.interfaces.rest.transform;


import org.upc.cobox.delivery.domain.model.commands.CreateOrderCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.CreateOrderResource;

import java.math.BigDecimal;
import java.util.Date;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommandFromResource(CreateOrderResource resource) {
        return new CreateOrderCommand(
                resource.clientId(),
                resource.addressLine(),
                resource.city(),
                resource.country(),
                resource.postalCode(),
                resource.referenceLatitude(),
                resource.referenceLongitude(),
                resource.notes(),
                resource.weightKg()
        );
    }


}


