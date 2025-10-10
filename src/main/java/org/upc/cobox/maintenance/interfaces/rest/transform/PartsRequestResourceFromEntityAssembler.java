// interfaces/rest/transform/PartsRequestResourceFromEntityAssembler.java
package org.upc.cobox.maintenance.interfaces.rest.transform;

import org.upc.cobox.maintenance.domain.model.entities.PartsRequest;
import org.upc.cobox.maintenance.interfaces.rest.resources.PartsRequestResource;

public class PartsRequestResourceFromEntityAssembler {
    public static PartsRequestResource toResource(PartsRequest p) {
        return new PartsRequestResource(
                p.getId(),
                p.getPartNumber(),
                p.getDescription(),
                p.getQuantity() != null ? p.getQuantity().getValue() : null,
                p.getQuantity() != null ? p.getQuantity().getUnit() : null,
                p.isReceived(),
                p.getSupplierId()
        );
    }
}
