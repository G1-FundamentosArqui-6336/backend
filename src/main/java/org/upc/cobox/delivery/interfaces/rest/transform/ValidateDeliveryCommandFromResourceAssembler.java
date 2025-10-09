package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.delivery.domain.model.commands.ValidateDeliveryCommand;
import org.upc.cobox.delivery.interfaces.rest.resources.ValidateDeliveryResource;

import java.util.Date;

public class ValidateDeliveryCommandFromResourceAssembler {

    public static ValidateDeliveryCommand toCommandFromResource(Long orderId, ValidateDeliveryResource r) {
        return new ValidateDeliveryCommand(
                orderId,
                r.receiverName(),
                r.photoUrl(),
                r.signatureCode(),
                r.takenAt() == null ? new Date() : r.takenAt()
        );
    }
}