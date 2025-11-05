package org.upc.cobox.delivery.application.internal.outboundservices;

public interface ExternalRouteService {
    void addDeliveredOrderToRoute(Long routeId, Long orderId);
}
