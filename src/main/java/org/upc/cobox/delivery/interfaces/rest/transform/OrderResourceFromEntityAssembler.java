package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.Incident.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.domain.model.aggregates.Order;
import org.upc.cobox.delivery.interfaces.rest.resources.OrderResource;

public class OrderResourceFromEntityAssembler {

    public static OrderResource toResourceFromEntity(Order o) {
        Evidence ev = o.getEvidence();
        OrderResource.EvidenceResource evRes = ev == null ? null :
                new OrderResource.EvidenceResource(
                        ev.getReceiverName(),
                        ev.getPhotoUrl(),
                        ev.getSignatureCode(),
                        ev.getTakenAt()
                );

        return new OrderResource(
                o.getId(),
                o.getClientId().getClientId(),
                o.getAddress().getLine(),
                o.getAddress().getCity(),
                o.getAddress().getCountry(),
                o.getAddress().getPostalCode(),
                o.getReference().getReference(),
                o.getScheduledAt().getScheduledAt(),
                o.getDeliveredAt() == null ? null : o.getDeliveredAt().getDeliveredAt(),
                o.getStatus().name(),
                o.getNotes() == null ? null : o.getNotes().getNotes(),
                o.getTotalWeight().getWeightKg(),
                evRes
        );
    }
}
