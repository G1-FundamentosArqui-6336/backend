package org.upc.cobox.delivery.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import org.upc.cobox.delivery.application.internal.outboundservices.ExternalRouteService;
import org.upc.cobox.fleet.interfaces.acl.RouteContextFacade;


@Service
public class ExternalRouteServiceImpl implements ExternalRouteService {
    private final RouteContextFacade routeContextFacade;
    public ExternalRouteServiceImpl(RouteContextFacade routeContextFacade) {
        this.routeContextFacade = routeContextFacade;
    }

    public  void addDeliveredOrderToRoute(Long routeId, Long orderId){
        routeContextFacade.addDeliveredOrderToRoute(routeId,orderId);
    }

}
