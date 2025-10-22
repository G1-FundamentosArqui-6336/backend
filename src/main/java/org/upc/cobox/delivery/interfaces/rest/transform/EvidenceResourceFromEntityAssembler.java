package org.upc.cobox.delivery.interfaces.rest.transform;

import org.upc.cobox.delivery.domain.model.aggregates.Evidence;
import org.upc.cobox.delivery.interfaces.rest.resources.EvidenceResource;


public class EvidenceResourceFromEntityAssembler {
    public static EvidenceResource toResourceFromEntity(Evidence entity) {
        return new EvidenceResource(
                entity.getId(),
                entity.getReceiverName(),
                entity.getPhotoUrl()
            );
    }
}
