package org.upc.cobox.delivery.interfaces.rest.resources;

public record MarkAsCompletedOrderResource(
                                                  Long routeId,
                                                  String photoUrl,
                                                  String receiverName,
                                                  String signatureData) {
}
