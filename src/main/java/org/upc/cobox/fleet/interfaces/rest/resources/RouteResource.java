package org.upc.cobox.fleet.interfaces.rest.resources;


import org.upc.cobox.fleet.domain.model.valueobjects.OrderId;

import java.util.List;
import java.util.Set;

public record RouteResource(
        Long id,
        String title,
        Long vehicleId,
        Long driverId,
        List<OrderId> ordersIds,
        Set<OrderId> finishedOrderIds,
        String routeStatus
) {

}
