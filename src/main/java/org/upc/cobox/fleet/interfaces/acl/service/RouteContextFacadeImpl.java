package org.upc.cobox.fleet.interfaces.acl.service;
import org.springframework.stereotype.Service;
import org.upc.cobox.fleet.domain.model.commands.AddDeliveredOrderToRouteCommand;
import org.upc.cobox.fleet.domain.services.RouteCommandService;
import org.upc.cobox.fleet.interfaces.acl.RouteContextFacade;

@Service
public class RouteContextFacadeImpl implements RouteContextFacade
{
    private final RouteCommandService routeCommandService;

    public RouteContextFacadeImpl(RouteCommandService routeCommandService) {
        this.routeCommandService = routeCommandService;
    }

    public  void addDeliveredOrderToRoute(Long routeId, Long orderId){
        var addDeliveredOrderToRouteCommand = new AddDeliveredOrderToRouteCommand(routeId,orderId);
        routeCommandService.handle(addDeliveredOrderToRouteCommand);
    }

}
