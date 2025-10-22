package org.upc.cobox.fleet.interfaces.acl;



public interface RouteContextFacade {

    void addDeliveredOrderToRoute(Long routeId, Long orderId);
}
