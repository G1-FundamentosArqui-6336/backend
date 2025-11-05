package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.delivery.domain.model.commands.MarkAsCompletedOrderCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.MarkAsCompletedOrderResource;


public class MarkAsCompletedOrderCommandFromResourceAssembler {

    public static MarkAsCompletedOrderCommand toCommandFromResource(Long orderId, MarkAsCompletedOrderResource resource) {
        return new MarkAsCompletedOrderCommand(
                orderId,
                resource.routeId(),
                resource.photoUrl(),
                resource.receiverName(),
                resource.signatureData()
        );
    }
}