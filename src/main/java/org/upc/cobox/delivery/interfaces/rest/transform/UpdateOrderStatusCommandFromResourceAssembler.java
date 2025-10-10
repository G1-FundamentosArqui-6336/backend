package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.delivery.domain.model.commands.UpdateOrderStatusCommand;
import org.upc.cobox.delivery.domain.model.valueobjects.DeliveryStatus;
import org.upc.cobox.delivery.interfaces.rest.resources.UpdateOrderStatusResource;

import java.util.Locale;

public class UpdateOrderStatusCommandFromResourceAssembler {

    public static UpdateOrderStatusCommand toCommandFromResource(Long clientId,Long orderId, UpdateOrderStatusResource r) {
        DeliveryStatus status = DeliveryStatus.valueOf(r.status().trim().toUpperCase(Locale.ROOT));
        return new UpdateOrderStatusCommand(clientId,orderId, status);
    }
}