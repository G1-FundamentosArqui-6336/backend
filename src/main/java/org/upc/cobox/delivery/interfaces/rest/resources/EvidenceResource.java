package org.upc.cobox.delivery.interfaces.rest.resources;

public record EvidenceResource(
        Long id,
        String receiverName,
        String photoUrl
) {
}
